# Mini Quiz System

A backend REST API for an online quiz system, built with Spring Boot.

This project is developed in two phases. **Phase 1** focuses on the core quiz business logic, while **Phase 2** will extend the system with authentication, authorization, JWT-based security, and Docker.

## Features

### Quiz Management

* Create, update, retrieve, and delete quizzes
* Add questions and choices to quizzes
* Publish quizzes with business rule validation
* Manage quiz status and duration

### Quiz Taking

* Start a quiz and create a submission
* Submit answers for questions
* Validate submitted answers
* Finish a submission
* Calculate the final score

## Business Rules

Business logic is the main focus of Phase 1. The system does not simply perform CRUD operations; important operations are validated according to the quiz workflow.

### Quiz Publishing

A quiz can only be published when all required conditions are satisfied:

* The quiz must exist.
* The quiz must currently be in `DRAFT` status.
* The quiz must contain at least one question.
* Every question must contain at least two choices.
* Every question must have exactly one correct choice.

Only after all conditions are satisfied does the quiz move from `DRAFT` to `PUBLISHED`.

### Starting a Quiz

A user can start a quiz only when:

* The quiz exists.
* The quiz is currently `PUBLISHED`.

Starting a quiz creates a new `Submission` and records its start time.

### Submitting Answers

Answers are validated against the quiz structure and submission:

* The submission must exist.
* The submission must still be active.
* The selected choice must belong to the corresponding question.
* A question cannot be answered more than once.
* Answers can only be submitted while the submission is within its allowed time.

### Finishing a Submission

A submission can only be finished when:

* The submission exists.
* The submission has not already been finished.
* The time limit has not expired.
* All questions have been answered.

After finishing:

* The submission receives an `endTime`.
* The final score is calculated from the submitted answers.
* The completed submission is returned to the client.

These rules are implemented in the service layer rather than being treated as simple database operations.

## Validation & Exception Handling

* Bean Validation for request validation
* Custom exceptions for domain-specific errors
* Global exception handling
* Business rule validation
* Transaction management

## API Documentation

* OpenAPI / Swagger documentation
* Interactive API testing through Swagger UI

## Testing

Unit tests are focused on the application's **business logic methods in the service layer**.

* JUnit 5
* Mockito
* Service-layer unit testing
* Testing successful business flows
* Testing business rule violations
* Testing expected exceptions

## Tech Stack

* Java 21
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* JUnit 5
* Mockito
* OpenAPI / Swagger

## Project Structure

The project follows a layered architecture:

```text
src
├── main
│   ├── java
│   │   └── com.shah.mini_quiz_system
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       ├── entity
│   │       ├── dto
│   │       ├── mapper
│   │       ├── exception
│   │       └── config
│   │
│   └── resources
│       └── application.yaml
│
└── test
    └── java
        └── com.shah.mini_quiz_system
            └── service
```

### Layer Responsibilities

* **Controller** — Handles HTTP requests and responses.
* **Service** — Contains business logic and application rules.
* **Repository** — Handles database access through Spring Data JPA.
* **Entity** — Represents the database domain model.
* **DTO** — Defines request and response models for the API.
* **Mapper** — Converts between entities and DTOs.
* **Exception** — Contains custom exceptions and global exception handling.
* **Config** — Contains application configuration.

## Domain Model

```text
Quiz 1 ─── N Question
Question 1 ─── N Choice

Quiz 1 ─── N Submission
Submission 1 ─── N Answer

Answer N ─── 1 Question
Answer N ─── 1 Choice
```

## Quiz Lifecycle

```text
DRAFT → PUBLISHED → FINISHED
```

## Running the Project

### Requirements

* Java 21
* PostgreSQL
* Maven

### Database

Create a PostgreSQL database:

```sql
CREATE DATABASE mini_quiz;
```

Configure the database connection in `application.yaml`.

The database password is read from an environment variable rather than being stored directly in the repository.

### Run

On Windows:

```bash
mvnw.cmd spring-boot:run
```

## API Documentation

After running the application, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Phase 1 — Completed

Phase 1 focuses on the core backend functionality and business logic without authentication or authorization.

### Completed

* Quiz management
* Question and choice management
* Quiz publishing workflow
* Quiz submission workflow
* Answer submission
* Submission finishing
* Score calculation
* Business rule validation
* Bean Validation
* Exception handling
* Transaction management
* OpenAPI documentation
* Unit testing of core business logic methods with JUnit 5 and Mockito

## Phase 2 — Planned

Phase 2 will extend the existing backend with a security layer and deployment-related functionality.

### Planned Features

* Spring Security
* User registration and authentication
* Password hashing
* JWT-based authentication
* Role-based authorization
* Teacher and Student roles
* Secured API endpoints
* Access control for quiz management and quiz participation
* Docker containerization

The existing Phase 1 business logic will remain the foundation of the system while these security features are added on top of it.

## Project Goal

The goal of this project is to practice building a real-world Spring Boot backend with a focus on **business logic, JPA relationships, validation, exception handling, testing, and security**.

# Mini Quiz System — Phase 2

Phase 2 extends the project from a core quiz backend into a secured, containerized backend system. The main focus of this phase was **Authentication, Authorization, Spring Security, JWT, and Docker**.

---

## Table of Contents

- [Overview](#overview)
- [Authentication](#authentication)
- [Roles & Authorization](#roles--authorization)
- [Spring Security](#spring-security)
- [JWT Authentication Flow](#jwt-authentication-flow)
- [Docker](#docker)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Learning Experience](#learning-experience)
- [Project Status](#project-status)
- [Next Steps](#next-steps)

---

## Overview

Building on Phase 1 (core quiz system), Phase 2 adds a full authentication and authorization layer using Spring Security and JWT, and containerizes the application with Docker alongside a PostgreSQL database.

---

## Authentication

- User Registration
- User Login
- User Logout
- Password Hashing with BCrypt
- JWT-based Authentication

## Roles & Authorization

The system supports three roles:

- **Admin**
- **Teacher**
- **Student**

Role-Based Access Control (RBAC) restricts endpoints based on the authenticated user's role:

| Role    | Example Permissions              |
|---------|-----------------------------------|
| Admin   | Manage users                      |
| Teacher | Create/manage quizzes             |
| Student | Submit quiz answers               |

---

## Spring Security

Core components implemented and practiced in this phase:

- Security Filter Chain
- `AuthenticationManager`
- `UserDetails` / `UserDetailsService`
- `PasswordEncoder` (BCrypt)
- Custom JWT Authentication Filter
- `SecurityContext`
- Role-based endpoint authorization

### JWT Authentication Flow

```text
Login Request
      │
      ▼
AuthenticationManager
      │
      ▼
UserDetailsService ──► UserRepository ──► User
      │
      ▼
Authentication Object
      │
      ▼
JwtService ──► JWT Token
      │
      ▼
Authorization Header (Bearer Token)
      │
      ▼
JwtAuthenticationFilter
      │
      ▼
SecurityContext
      │
      ▼
Protected REST Endpoint
```

---

## Docker

The application was containerized together with PostgreSQL.

**Implemented and practiced:**

- Dockerfile
- Docker Images & Containers
- Port Mapping
- Environment Variables
- Docker Network (manual container-to-container communication)
- PostgreSQL Container with Persistent Volume
- Docker CLI workflow

### Docker Architecture

```text
Spring Boot Application Container
            │
            │  (Docker Network)
            ▼
    mini-quiz-postgres Container
            │
            ▼
    PostgreSQL Persistent Volume
```

### Build & Containerization Flow
```text
Source Code → Maven Build → JAR → Docker Image → Docker Container
```

### Why Docker CLI Instead of Docker Compose?

The application and PostgreSQL container were connected **manually** using the Docker CLI rather than Docker Compose. This was an intentional choice to first understand the underlying concepts — images, containers, networks, volumes, and environment variables — before moving to higher-level orchestration tools. Docker Compose is planned as the next step.

---

## Tech Stack

**Backend**
Java · Spring Boot · Spring MVC · Spring Data JPA · Hibernate · Spring Security · JWT · Bean Validation

**Database**
PostgreSQL

**Documentation & Testing**
OpenAPI / Swagger · JUnit 5 · Postman

**DevOps / Tools**
Maven · Docker · Docker CLI · Git · GitHub

---

## Project Structure

```text
mini-quiz-system
├── src
│   ├── main
│   │   ├── java/com/shah/mini_quiz_system
│   │   │   ├── config          # Security & app configuration
│   │   │   ├── controller      # REST controllers
│   │   │   ├── domain          # Entities
│   │   │   ├── dto             # Request/response DTOs
│   │   │   ├── exception       # Custom exceptions & global handler
│   │   │   ├── filter          # JWT authentication filter
│   │   │   ├── mapper          # MapStruct mappers
│   │   │   ├── repository      # Spring Data JPA repositories
│   │   │   ├── service         # Business logic
│   │   │   └── MiniQuizSystemApplication.java
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.yaml
│   └── test
├── Dockerfile
├── .gitignore
├── .gitattributes
└── pom.xml
```

### Request Flow

```text
Client
  │
  ▼
REST API (Controller)
  │
  ▼
Spring Security (Filter Chain + JWT Filter)
  │
  ▼
Service (Business Logic)
  │
  ▼
Repository (JPA)
  │
  ▼
PostgreSQL
```

---

## Learning Experience

This phase was my first practical implementation of several backend concepts I had previously only studied conceptually.

**Spring Security**
My first hands-on implementation of `AuthenticationManager`, `UserDetailsService`, `SecurityContext`, the Security Filter Chain, and a custom JWT filter. The focus wasn't just making login work — it was understanding how these components interact inside Spring Security's architecture.

**Docker**
Containerized the application manually (without Compose) to understand the relationship between the app container, the database container, the Docker network, persistent storage, and environment variables — before relying on higher-level tooling.

---

## Project Status

| Phase | Description | Status |
|-------|-------------|--------|
| Phase 1 | Core Quiz System | ✅ Completed |
| Phase 2 | Security & Docker | ✅ Completed |

## Next Steps

- Docker Compose
- Linux fundamentals
- GitHub Actions (CI/CD)
- Deployment to a Linux server
