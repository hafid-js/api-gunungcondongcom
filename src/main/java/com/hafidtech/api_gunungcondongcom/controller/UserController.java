package com.hafidtech.api_gunungcondongcom.controller;

import com.hafidtech.api_gunungcondongcom.exception.UserException;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import com.hafidtech.api_gunungcondongcom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException, UserException {

        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }


}
