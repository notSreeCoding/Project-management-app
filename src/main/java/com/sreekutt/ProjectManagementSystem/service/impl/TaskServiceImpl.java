package com.sreekutt.ProjectManagementSystem.service.impl;

import com.sreekutt.ProjectManagementSystem.entity.Project;
import com.sreekutt.ProjectManagementSystem.entity.Task;
import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.io.TaskRequest;
import com.sreekutt.ProjectManagementSystem.repo.ProjectRepository;
import com.sreekutt.ProjectManagementSystem.repo.TaskRepository;
import com.sreekutt.ProjectManagementSystem.repo.UserRepository;
import com.sreekutt.ProjectManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Task createTask(TaskRequest request, String creatorEmail) {


        UserEntity user = userRepository.findByEmail(request.getAssignedToEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found: "+request.getAssignedToEmail()));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(()->new UsernameNotFoundException("Project not found"+request.getProjectId()));

        if (!project.getCreatedBy().getEmail().equals(creatorEmail)){
            throw new SecurityException("You are not authorized to assign tasks for this project.");
        }

        Task task = convertToTask(request,project,user);
        return taskRepository.save(task);

    }

    @Override
    public List<Task> getTasksForProjectforCurrentUser(Long projectId, String currentUserEmail) {

        UserEntity user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(()->new UsernameNotFoundException("USer not FOund "+currentUserEmail));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new UsernameNotFoundException("Project not found"));

        return taskRepository.findByAssignedToAndProject(user,project);

    }

    @Override
    public Task updateStatus(Long taskId, String status, String currentUserEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new UsernameNotFoundException("Project not found"));

        UserEntity user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (!task.getAssignedTo().getId().equals(user.getId())){
            throw new SecurityException("NOT AUTHORIZED");
        }
        task.setStatus(status);
        return taskRepository.save(task);

    }

    @Override
    public List<Task> getAllTasks(String currentUserEmail) {
        UserEntity user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return taskRepository.findByAssignedTo(user);
    }

    @Override
    public List<Task> getAllTaskForProjectForTaskCreator(Long ProjectId, String email) {
        Project project = projectRepository.findById(ProjectId)
                .orElseThrow(()-> new UsernameNotFoundException("Project not found"));

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (!project.getCreatedBy().getId().equals(user.getId())){
            throw new SecurityException("Youre not authorized to do this");
        }

        return taskRepository.findByProject(project);
    }

    private Task convertToTask(TaskRequest request, Project project, UserEntity user) {
        return Task.builder()
                .title(request.getTitle())
                .assignedTo(user)
                .status(request.getStatus())
                .project(project)
                .build();
    }
}
