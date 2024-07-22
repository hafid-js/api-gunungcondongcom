package com.hafidtech.api_gunungcondongcom.service.impl;

import com.hafidtech.api_gunungcondongcom.exception.AppException;
import com.hafidtech.api_gunungcondongcom.exception.BadRequestException;
import com.hafidtech.api_gunungcondongcom.model.role.Role;
import com.hafidtech.api_gunungcondongcom.model.role.RoleName;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import com.hafidtech.api_gunungcondongcom.repository.RoleRepository;
import com.hafidtech.api_gunungcondongcom.repository.UserRepository;
import com.hafidtech.api_gunungcondongcom.response.ApiResponse;
import com.hafidtech.api_gunungcondongcom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Username is already taken");
            throw new BadRequestException(apiResponse);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Email is already taken");
            throw new BadRequestException(apiResponse);
        }

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);

        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }
}
