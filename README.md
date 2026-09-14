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
