# 🛠️ Project Management System

A Spring Boot-based RESTful application for managing users, projects, and tasks. It includes secure JWT-based authentication and role-based access control.

## 🔐 Features

- **User Authentication** (JWT)
- **Role-Based Authorization**
- **User Registration/Login/Logout**
- **Project Management**
    - Create, view, and delete projects
- **Task Management**
    - Assign tasks to yourself under specific projects
    - View all tasks for a project or yourself
    - Update task status

---

## 📦 Tech Stack

- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Authentication:** JWT, Cookies
- **Database:** H2 / MySQL (configurable)
- **Validation:** Jakarta Validation
- **Other:** Lombok, Maven


JWT authentication is applied using a custom `JwtFilter` and user details are handled via `AppUserDetailsService`.

---

## 🔧 Endpoints

### 🔐 Authentication

| Method | Endpoint         | Description         |
|--------|------------------|---------------------|
| POST   | `/api/register`  | Register new user   |
| POST   | `/api/login`     | Login & receive JWT |
| POST   | `/api/logout`    | Clear JWT cookie    |

---

### 📁 Project

| Method | Endpoint            | Description                  | Role Required |
|--------|---------------------|------------------------------|---------------|
| POST   | `/projects/create-project` | Create new project         | `ADMIN`       |
| GET    | `/projects/get-project`    | List your projects         | `ADMIN`       |
| DELETE | `/projects/delete-project` | Delete project by ID       | `ADMIN`       |

---

### 📋 Task

| Method | Endpoint                               | Description                                |
|--------|----------------------------------------|--------------------------------------------|
| POST   | `/tasks`                               | Create new task                            |
| GET    | `/tasks/me`                            | Get your own tasks                         |
| GET    | `/tasks/project/{projectId}`           | Get tasks in a project (your own)          |
| PATCH  | `/tasks/{taskId}?status={status}`      | Update task status                         |
| GET    | `/tasks/project/{projectId}/all`       | Get all tasks in a project (if you're owner) |

---
