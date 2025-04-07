## Spring Boot Security Authentication

This repository contains a Java-based web application that leverages Spring Boot and Spring Security to provide a robust authentication system. It includes features such as user registration, JWT token management, and secure password encryption. The application is built with Maven and follows standard Spring Boot conventions, making it easy to extend and integrate with other Spring-based components.

## Features

- **User Registration and Authentication**
    - `RegisterRequest` handles user registration details.
    - `ApplicationConfig` sets up `UserDetailsService` to load user-specific data.

- **JWT Token Management**
    - `JwtService` is responsible for generating, validating, and parsing JWTs for secure session handling.

- **Security Configuration**
    - `SecurityConfiguration` defines the security filter chain, disables CSRF, sets stateless session management, and adds a custom JWT filter.

- **Password Encryption**
    - Secure password hashing using `BCryptPasswordEncoder` via the `authenticationProvider` bean.

## Getting Started - NB:Ensure Docker is running before starting this project

### Prerequisites

- Java 17 or higher
- Maven 3.4.4 or higher

### Installation

```bash
1. Clone the repository
git clone https://github.com/marc-bhunu/spring-boot-auth-jwt.git

2. Navigate to the project directory
cd spring-boot-auth-jwt

3. Build the project
mvn clean install

4. Running the Application
mvn spring-boot:run
```

The application will start at: `http://localhost:9000`

## API Endpoints

- `POST /api/v1/auth/register` — User Registration
- `POST /api/v1/auth/authenticate` — User Login
- `POST /api/v1/auth/verify` — Email Verification
- `GET /api/v1/auth/resend-verification-code` — Resend Verification Code

## Testing the Endpoints

### User Registration

```bash
Invoke-RestMethod -Uri "http://localhost:9000/api/v1/auth/register" `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body '{
    "firstName": "John",
    "lastName": "Jonas",
    "email": "mac@gmail.com",
    "password": "1234"
  }'
```

### User Authentication

```bash
Invoke-RestMethod -Uri "http://localhost:9000/api/v1/auth/authenticate" `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body '{
    "email": "user@email.com",
    "password": "1234"
  }'
```

### Verify Email
```bash
Invoke-RestMethod -Uri "http://localhost:9000/api/v1/auth/verify" `
  -Method POST `
  -Headers @{
    "Content-Type" = "application/json"
    "Authorization" = "Bearer <your_token_here>"
  } `
  -Body '{
    "email": "user@email.com",
    "verificationCode": "12349909"
  }'
```

### Resend Verification Code
```bash
Invoke-RestMethod -Uri "http://localhost:9000/api/v1/auth/verify/" `
  -Method POST `
  -Headers @{
    "Content-Type" = "application/json"
    "Authorization" = "Bearer <your_token_here>"
  } `
  -Body '{
    "email": "user@email.com",
    "verificationCode": $null
  }'
```

## Configuration

- Security configuration:  
  `src/main/java/com/demo/security/config/SecurityConfiguration.java`

- JWT service and utilities:  
  `src/main/java/com/demo/security/config/JwtService.java`

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

