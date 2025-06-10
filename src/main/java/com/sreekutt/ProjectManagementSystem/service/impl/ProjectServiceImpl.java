package com.sreekutt.ProjectManagementSystem.service.impl;

import com.sreekutt.ProjectManagementSystem.entity.Project;
import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.io.ProjectRequest;
import com.sreekutt.ProjectManagementSystem.io.ProjectResponse;
import com.sreekutt.ProjectManagementSystem.repo.ProjectRepository;
import com.sreekutt.ProjectManagementSystem.repo.UserRepository;
import com.sreekutt.ProjectManagementSystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    @Override
    public ProjectResponse CreateProject(ProjectRequest request, String email) {

        UserEntity owner = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"+email));

        Project project = convertToEntity(request,owner);
        projectRepository.save(project);
        return convertToResponse(project,owner);
    }

    @Override
    public List<ProjectResponse> getProject(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"+email));

        List<Project> projects = projectRepository.findByCreatedBy(user);

        return projects.stream()
                .map(project -> {
                    return convertToResponse(project, user);
                })
                .collect(Collectors.toList());

    }

    @Override
    public void deleteProject(Long id, String email) {

        UserEntity owner = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+email));

        Project project = projectRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Project not fonud"+id));

        if (!project.getCreatedBy().getId().equals(owner.getId())){
            throw new SecurityException("Unauthorized");
        }

        projectRepository.delete(project);
    }

    private ProjectRequest convertToRequest(Project project) {
        return ProjectRequest.builder()
                .deadline(project.getDeadline())
                .description(project.getDescription())
                .name(project.getName())
                .build();
    }

    private ProjectResponse convertToResponse(Project project, UserEntity owner) {
        return ProjectResponse.builder()
                .nameOfProject(project.getName())
                .deadline(project.getDeadline())
                .nameOfCreator(owner.getUsername())
                .description(project.getDescription())
                .id(project.getId())
                .build();
    }


    private Project convertToEntity(ProjectRequest request, UserEntity owner) {
        return Project.builder()
                .name(request.getName())
                .createdBy(owner)
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .build();
    }
}
