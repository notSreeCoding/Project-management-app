package com.sreekutt.ProjectManagementSystem.controller;

//import com.sreekutt.ProjectManagementSystem.entity.Task;
import com.sreekutt.ProjectManagementSystem.entity.Task;
import com.sreekutt.ProjectManagementSystem.io.TaskRequest;
import com.sreekutt.ProjectManagementSystem.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task createTask(@Valid @RequestBody TaskRequest request, @CurrentSecurityContext(expression = "Authentication?.name")String creatorEmail){
        return taskService.createTask(request,creatorEmail);
    }

    @GetMapping("/me")
    public List<Task> getAllTaskForCurrentUser(@CurrentSecurityContext(expression = "Authentication?.name")String email){
        return taskService.getAllTasks(email);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getAllTasksForCurrentUserForProject(@PathVariable Long projectId, @CurrentSecurityContext(expression = "Authentication?.name")String email){
        return taskService.getTasksForProjectforCurrentUser(projectId,email);
    }

    @PatchMapping
    public Task updateTaskStatus(@PathVariable Long taskId,
                                 @RequestParam String status,
                                 @CurrentSecurityContext(expression = "Authentication?.name")String email){
        return taskService.updateStatus(taskId,status,email);
    }

    @GetMapping("/project/{projectId}/all")
    public List<Task> getAllTasksForProjectForCreator(@PathVariable Long projectId,
                                                      @CurrentSecurityContext(expression = "Authentication?.name")String email){
        return taskService.getAllTaskForProjectForTaskCreator(projectId, email);
    }





}
