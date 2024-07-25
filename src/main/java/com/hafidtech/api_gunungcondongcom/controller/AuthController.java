package com.hafidtech.api_gunungcondongcom.controller;

import com.hafidtech.api_gunungcondongcom.config.JwtProvider;
import com.hafidtech.api_gunungcondongcom.exception.BadRequestException;
import com.hafidtech.api_gunungcondongcom.exception.UserException;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import com.hafidtech.api_gunungcondongcom.repository.UserRepository;
import com.hafidtech.api_gunungcondongcom.request.LoginRequest;
import com.hafidtech.api_gunungcondongcom.response.ApiResponse;
import com.hafidtech.api_gunungcondongcom.response.AuthResponse;
import com.hafidtech.api_gunungcondongcom.response.LoginResponse;
import com.hafidtech.api_gunungcondongcom.service.UserService;
import com.hafidtech.api_gunungcondongcom.service.impl.CustomerUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

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


    @PostMapping("/user/signup")
    @ResponseBody
    public ResponseEntity<AuthResponse> addUser(@Valid @RequestBody User user) throws UserException {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserException("Username is already used with another account");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("Email is already used with another account");
        }
        User newUser = userService.addUser(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(newUser);
        authResponse.setMessage("Signup success! Please Login");
        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
    }


    @PostMapping("/admin/signup")
    public ResponseEntity<Object> addAdmin(@Valid @RequestBody User user) throws UserException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserException("Username is already used with another account");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("Email is already used with another account");
        }
        User newUser = userService.addAdmin(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(newUser);
        authResponse.setMessage("Success Add Admin!");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> loginUserHandler(@Valid @RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(token);
        loginResponse.setMessage("Signin Successfully");

        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
    }



    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customerUserService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}