package com.sreekutt.ProjectManagementSystem.repo;

import com.sreekutt.ProjectManagementSystem.entity.Project;
import com.sreekutt.ProjectManagementSystem.entity.Task;
import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByProject(Project project);
    List<Task> findByAssignedToAndProject(UserEntity user,Project project);
    List<Task> findByAssignedTo(UserEntity user);
}
