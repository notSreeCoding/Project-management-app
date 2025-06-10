package com.sreekutt.ProjectManagementSystem.service;

import com.sreekutt.ProjectManagementSystem.entity.Project;
import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.io.ProjectRequest;
import com.sreekutt.ProjectManagementSystem.io.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse CreateProject(ProjectRequest request, String email);
    List<ProjectResponse> getProject(String email);
    void deleteProject(Long id, String email);
}
