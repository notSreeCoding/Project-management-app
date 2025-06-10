package com.sreekutt.ProjectManagementSystem.controller;

import com.sreekutt.ProjectManagementSystem.entity.Project;
import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.io.ProjectRequest;
import com.sreekutt.ProjectManagementSystem.io.ProjectResponse;
import com.sreekutt.ProjectManagementSystem.service.ProjectService;
import com.sreekutt.ProjectManagementSystem.service.UserEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService service;

    private final UserEntityService userEntityService;
    @PostMapping("/create-project")
    public ProjectResponse createProject(@Valid @RequestBody ProjectRequest request,
                                         @CurrentSecurityContext(expression = "Authentication?.name")String email){

        return service.CreateProject(request,email);
    }

    @GetMapping("/get-project")
    public List<ProjectResponse> getProject(@CurrentSecurityContext(expression = "Authentication?.name")String email){
        return service.getProject(email);
    }

    @DeleteMapping("/delete-project")
    public ResponseEntity<?> deleteProject(@RequestBody Long id, @CurrentSecurityContext(expression = "Authentication?.name")String email){

        service.deleteProject(id,email);
        return ResponseEntity.ok().body("The project with id: "+id+", Successfully deleted.");
    }
}
