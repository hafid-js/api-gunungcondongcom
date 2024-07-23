package com.hafidtech.api_gunungcondongcom.controller;

import com.hafidtech.api_gunungcondongcom.config.JwtProvider;
import com.hafidtech.api_gunungcondongcom.exception.UserException;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import com.hafidtech.api_gunungcondongcom.repository.UserRepository;
import com.hafidtech.api_gunungcondongcom.response.AuthResponse;
import com.hafidtech.api_gunungcondongcom.response.CommonResponseBean;
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

import java.util.Date;

@RestController
@RequestMapping("/api/admin")
public class UserController {

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

    @PostMapping("/signup")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) throws UserException {
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
    public ResponseEntity<LoginResponse> loginUserHandler(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();

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
