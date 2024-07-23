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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public User addAdmin(User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
