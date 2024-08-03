package com.hafidtech.api_gunungcondongcom.controller;

import com.hafidtech.api_gunungcondongcom.config.JwtProvider;
import com.hafidtech.api_gunungcondongcom.event.RegistrationCompleteEvent;
import com.hafidtech.api_gunungcondongcom.event.listener.RegistrationCompleteEventListener;
import com.hafidtech.api_gunungcondongcom.exception.AppException;
import com.hafidtech.api_gunungcondongcom.exception.LoginException;
import com.hafidtech.api_gunungcondongcom.exception.UserException;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import com.hafidtech.api_gunungcondongcom.registration.password.PasswordResetRequest;
import com.hafidtech.api_gunungcondongcom.registration.password.PasswordResetTokenRepository;
import com.hafidtech.api_gunungcondongcom.registration.token.VerificationToken;
import com.hafidtech.api_gunungcondongcom.registration.token.VerificationTokenRepository;
import com.hafidtech.api_gunungcondongcom.repository.user.UserRepository;
import com.hafidtech.api_gunungcondongcom.request.LoginRequest;
import com.hafidtech.api_gunungcondongcom.response.AuthResponse;
import com.hafidtech.api_gunungcondongcom.response.ErrorBean;
import com.hafidtech.api_gunungcondongcom.response.LoginResponse;
import com.hafidtech.api_gunungcondongcom.service.UserService;
import com.hafidtech.api_gunungcondongcom.service.impl.CustomerUserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserServiceImpl customerUserService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private RegistrationCompleteEventListener eventListener;
    @Autowired
    private HttpServletRequest servletRequest;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @PostMapping("/user/signup")
    @ResponseBody
    public Object userRegister(@Valid @RequestBody User register, final HttpServletRequest request) throws UserException {

        if (userRepository.existsByUsername(register.getUsername())) {
            throw new UserException("Username is already used with another account");
        }
        if (userRepository.existsByEmail(register.getEmail())) {
            throw new UserException("Email is already used with another account");
        }
        User user = userService.addUser(register);

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success! Please, Check your email for complete your registration";
    }


    @PostMapping("/admin/signup")
    public Object adminRegister(@Valid @RequestBody User register, final HttpServletRequest request) throws UserException {

        if (userRepository.existsByUsername(register.getUsername())) {
            throw new UserException("Username is already used with another account");
        }
        if (userRepository.existsByEmail(register.getEmail())) {
            throw new UserException("Email is already used with another account");
        }
        User user = userService.addAdmin(register);

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success! Please, Check your email for complete your registration";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    @GetMapping("/verifyEmail")
    public String sendVerificationToken(@RequestParam("token") String token) {
        String url = applicationUrl(servletRequest)+"/api/auth/resend-verification-token?token="+token;


        VerificationToken theToken = tokenRepository.findByToken(token);
        if(theToken.getUser().isEnabled()) {
            return "This account has already been verified, please login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification link, <a href=\"" +url+ "\"> Get a new verification link. </a>";
    }


    @GetMapping("/resend-verification-token")
    public String resendVerificationCode(@RequestParam("token") String oldToken, final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return "A new verification link has been sent to your email." +
                " please check to activate your account";

    }

    private void resendVerificationTokenEmail(User theUser, String applicationUrl, VerificationToken token) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/auth/verifyEmail?token="+token.getToken();
        eventListener.sendVerificationEmail(url, theUser);
        log.info("Click the link to verify your registration : {}", url);
    }


    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> loginUserHandler(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User checkUsername = userRepository.findByEmail(username);
        if (checkUsername == null) {
            throw new LoginException("Invalid Username or Email...");
        }

        boolean isEnabled = false;
        Optional<User> checkEmailStatus = userRepository.findByEmailAndIsEnabled(username, isEnabled);
        if (checkEmailStatus.isPresent() && checkEmailStatus.get().isEnabled() == false) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setMessage("You're need confirm your email before login");

            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(token);
        loginResponse.setMessage("Signin Successfully");

        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
    }



    private Authentication authenticate(String username, String password) throws Exception {

        UserDetails userDetails = customerUserService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new LoginException("Invalid Password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest,
                                       final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException, UserException {
        Optional<User> user = userService.findByEmail(passwordResetRequest.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
        }
        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/auth/reset-password?token="+passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url, user);
        log.info("Click the link to reset your password : {}", url);
        return url;
    }

    @Transactional
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String passwordResetToken) {
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);
        if (!tokenValidationResult.equalsIgnoreCase("valid")) {
            passwordResetTokenRepository.deleteByToken(passwordResetToken);
            return "Invalid password reset token";
        }

        User user = userService.findUserProfileByToken(passwordResetToken);
        if (user != null) {
            userService.resetUserPassword(user, passwordResetRequest.getNewPassword());
            passwordResetTokenRepository.deleteByToken(passwordResetToken);
            return "Password has been reset successfully";
        }
        return "Invalid password reset token";
    }







}