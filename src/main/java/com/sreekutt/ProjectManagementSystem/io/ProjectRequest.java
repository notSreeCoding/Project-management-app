package com.sreekutt.ProjectManagementSystem.io;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {
    @NotBlank(message = "Project name cannot be blank")
    private String name;
    @NotBlank(message = "This field cannot be blank")
    private String description;
    @NotBlank(message = "Enter a valid deadline")
    private LocalDate deadline;
}
