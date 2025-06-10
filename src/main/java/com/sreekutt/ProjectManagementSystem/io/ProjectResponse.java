package com.sreekutt.ProjectManagementSystem.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {
    String nameOfProject;
    LocalDate deadline;
    String description;
    String nameOfCreator;
    Long id;
}
