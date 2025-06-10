package com.sreekutt.ProjectManagementSystem.service;

import com.sreekutt.ProjectManagementSystem.entity.Task;
import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.io.TaskRequest;

import java.util.List;

public interface TaskService {
    Task createTask(TaskRequest request, String creatorEmail);
    List<Task> getTasksForProjectforCurrentUser(Long projectId, String currentUserEmail);
    Task updateStatus(Long taskId, String status, String currentUserEmail);
    List<Task> getAllTasks(String currentUserEmail);
    List<Task> getAllTaskForProjectForTaskCreator(Long ProjectId,String email);
}
