Spring Boot Security - JWT Authentication Project

This is a Spring Boot application that demonstrates JWT-based authentication and authorization using Spring Security 6. The app includes a secure login flow, token generation, and role-based access control.

🔐 Features

- User login and registration with Spring Security
- JWT token generation and validation
- Secure REST APIs with protected endpoints
- Role-based access (admin/user)
- Custom user details service
- Password encoding using BCrypt
- MySQL Database integration using MySQL Workbench

🛠️ Technologies Used

- Java 17
- Spring Boot 3
- Spring Security 6
- JWT (io.jsonwebtoken - JJWT)
- MySQL Workbench
- Maven
- Eclipse IDE

📁 Project Structure

src/
├── main/
│   ├── java/
│   │   └── com.example.spring_security_6/
│   │       ├── config/              # Security configurations
│   │       ├── controller/          # REST controllers
│   │       ├── entity/              # User entity
│   │       ├── repository/          # JPA repositories
│   │       ├── service/             # JWT and User services
│   └── resources/
│       └── application.properties   # App config

 Getting Started
1. Clone the repository:
git clone https://github.com/NiraleeK/Springboot_Security.git
2. Import into Eclipse as a Maven project.
3. Open MySQL Workbench and create a database named `jwt_security_demo`.
4. In `application.properties`, update the database credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/jwt_security_demo
spring.datasource.username=your_username
spring.datasource.password=your_password

5. Run the application:
mvn spring-boot:run
6. Use Postman or browser to access the endpoints: `/register`, `/login`, `/welcome`
🧪 Example Credentials

| Role  | Username | Password   |
|-------|----------|------------|
| User  | user1    | pass1      |
| Admin | admin1   | adminpass  |

📌 Notes

- Make sure MySQL service is running before starting the Spring Boot application.
- JWT token is returned on successful login and must be used in the Authorization header as `Bearer <token>`.
- Spring Security is configured using custom filters and services to handle JWT processing.

