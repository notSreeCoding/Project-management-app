package com.sreekutt.ProjectManagementSystem.repo;

import com.sreekutt.ProjectManagementSystem.entity.Project;
import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.io.ProjectResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByCreatedBy(UserEntity owner);
}
