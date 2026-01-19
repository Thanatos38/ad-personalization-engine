📌 Overview

The Ad Personalization Engine is a Spring Boot–based backend application designed to demonstrate production-ready backend development practices. The project focuses on building scalable REST APIs with clean architecture, request interception, rate limiting, and structured logging.

This application is intended as a backend engineering showcase, highlighting real-world concerns such as API protection, observability, maintainability, and configuration management.

🎯 Project Goals

Build a clean and modular Spring Boot backend

Demonstrate interceptor-based request handling

Implement rate limiting to protect APIs

Follow industry-standard backend architecture

Write readable, maintainable, and scalable code

🏗 Architecture

The application follows a layered architecture:

Controller Layer  →  Service Layer  →  Repository Layer  →  Database
                         ↓
                  Interceptors

Layers Explained

Controller: Exposes REST endpoints and handles HTTP requests/responses

Service: Contains business logic and application workflows

Repository: Handles data persistence and database interactions

Interceptors: Cross-cutting concerns like logging and rate limiting

⚙️ Key Features

RESTful API development using Spring MVC

Request logging using Spring Interceptors

Rate limiting to control excessive API usage

Centralized exception handling

Configuration externalization using YAML

Maven-based project structure

Clean and readable codebase

🔐 Rate Limiting

The application includes a custom rate limiting interceptor that:

Tracks incoming requests per client/IP

Restricts excessive calls within a defined time window

Protects backend APIs from abuse

Improves system stability and performance

📊 Request Logging

Every incoming request is logged with:

Request URI

HTTP method

Timestamp

Request identifier (UUID)

This improves debugging, monitoring, and traceability.

🛠 Technology Stack

Language: Java

Framework: Spring Boot

Build Tool: Maven

Web: Spring MVC

Configuration: YAML

Logging: SLF4J

Version Control: Git & GitHub

🚀 Getting Started
Prerequisites

Java 17 (recommended)

Maven 3+

Git

Installation & Run
# Clone repository
git clone https://github.com/YOUR_USERNAME/ad-personalization-engine.git

# Navigate to project directory
cd ad-personalization-engine

# Build project
mvn clean install

# Run application
mvn spring-boot:run

⚙️ Configuration

Sensitive configuration files are excluded from version control.

Create your own configuration file:

application.yml


Or use the provided template:

application-example.yml


Update database credentials and environment-specific values before running.

🧪 API Testing

You can test the APIs using:

Postman

cURL

Browser (for GET endpoints)

Example:

curl http://localhost:8080/health

📁 Project Structure
src/main/java
 └── com.example.ad_personalization_engine
     ├── controller
     ├── service
     ├── repository
     ├── interceptor
     └── config

🧩 Future Enhancements

Authentication & authorization (JWT)

Database integration for analytics

Distributed rate limiting (Redis)

API documentation using Swagger/OpenAPI

Metrics & monitoring integration

👨‍💻 Author

Khushal Vyas
Backend Developer | Java | Spring Boot
