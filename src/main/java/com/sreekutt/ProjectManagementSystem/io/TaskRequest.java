package com.sreekutt.ProjectManagementSystem.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String status;
    @NotBlank
    private Long projectId;
    @Email
    private String assignedToEmail;
}
