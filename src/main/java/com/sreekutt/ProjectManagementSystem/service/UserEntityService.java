package com.sreekutt.ProjectManagementSystem.service;

import com.sreekutt.ProjectManagementSystem.io.AuthRequest;
import com.sreekutt.ProjectManagementSystem.io.RegReq;
import com.sreekutt.ProjectManagementSystem.io.UserResponse;

public interface UserEntityService {
    UserResponse registerUser(RegReq req);
    void login(AuthRequest request);
}
