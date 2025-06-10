package com.sreekutt.ProjectManagementSystem.controller;

import com.sreekutt.ProjectManagementSystem.io.RegReq;
import com.sreekutt.ProjectManagementSystem.io.UserResponse;
import com.sreekutt.ProjectManagementSystem.service.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserEntityController {

    private UserEntityService userEntityService;

    @PostMapping ("/register")
    public UserResponse registerUser(@RequestBody RegReq req){
        return userEntityService.registerUser(req);
    }
}
