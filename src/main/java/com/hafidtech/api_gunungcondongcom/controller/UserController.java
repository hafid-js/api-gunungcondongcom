package com.hafidtech.api_gunungcondongcom.controller;

import com.hafidtech.api_gunungcondongcom.model.user.User;
import com.hafidtech.api_gunungcondongcom.response.CommonResponseBean;
import com.hafidtech.api_gunungcondongcom.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        Object newUser = userService.addUser(user);


        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
