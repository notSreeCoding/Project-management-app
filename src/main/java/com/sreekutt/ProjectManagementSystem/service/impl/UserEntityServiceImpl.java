package com.sreekutt.ProjectManagementSystem.service.impl;

import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.io.AuthRequest;
import com.sreekutt.ProjectManagementSystem.io.RegReq;
import com.sreekutt.ProjectManagementSystem.io.UserResponse;
import com.sreekutt.ProjectManagementSystem.repo.UserRepository;
import com.sreekutt.ProjectManagementSystem.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(RegReq req) {
        UserEntity user = createEntity(req);
        userRepository.save(user);
        return convertToResponse(user);
    }

    @Override
    public void login(AuthRequest request) {

    }

    private UserResponse convertToResponse(UserEntity user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    private UserEntity createEntity(RegReq req) {
         return UserEntity.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .username(req.getUsername())
                 .build();
    }
}
