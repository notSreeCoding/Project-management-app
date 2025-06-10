package com.sreekutt.ProjectManagementSystem.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegReq {
    private String email;
    private String username;
    private String role;
    private String password;

}
