package com.sreekutt.ProjectManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String status;

    @ManyToOne
    private Project project;

    @ManyToOne
    private UserEntity assignedTo;
}
