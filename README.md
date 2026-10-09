# Honor Code – Academic Learning Platform

## 1. Project Overview
Honor Code is a simple academic learning platform built in Java using Java Servlets, JSP, JDBC, and MySQL. It helps a college to manage students, courses, materials, assignments, and basic results in a beginner-friendly way.

This project is designed for a college assignment and is intentionally simple. The focus is on understanding Java web development, JDBC database operations, and MVC-style logic without over-engineering.

## 2. Problem Statement
Many colleges need a light system where teachers can publish course content and students can enroll, study material, submit assignments, and check progress. A full enterprise system is too heavy for a student project, so this project solves the problem with a compact web application.

## 3. Features
- Student registration and a dedicated student login page (`/student-login`)
- Teacher registration and a dedicated teacher login page (`/teacher-login`)
- Honor Code landing page with links to student and teacher entry points
- Dashboard for students and teachers
- Course creation by teachers
- Student course enrollment
- Study material upload by teachers
- Assignment creation by teachers
- Assignment submission by students
- Timed Java fundamentals assessment with server-side scoring and result tracking
- Basic result tracking
- Basic validation for empty fields and duplicate usernames

## 4. Technology Stack
- Java 17
- Maven
- JSP
- Java Servlets
- JDBC
- MySQL
- Apache Tomcat
- HTML, CSS, and basic JavaScript

## 5. Project Structure
```text
honor-code/
├── database/
│   └── honor_code.sql
├── src/
│   ├── main/
│   │   ├── java/com/honorcode/
│   │   │   ├── dao/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── servlet/
│   │   │   └── util/
│   │   └── webapp/
│   │       ├── css/
│   │       ├── js/
│   │       ├── WEB-INF/
│   │       ├── *.jsp
│   │       └── *.jsp
├── .gitignore
├── pom.xml
├── README.md
└── target/
```

## 6. Database Setup
Open MySQL and run:

```sql
CREATE DATABASE honor_code;
USE honor_code;
SOURCE database/honor_code.sql;
```

> **Warning:** `honor_code.sql` drops and recreates the project tables. Back up any existing project data before running it again.

The SQL script creates the required tables:
- users
- courses
- enrollments
- materials
- assignments
- submissions
- results

## 7. MySQL Configuration
The application reads database details from environment variables if available. Default values are:

```text
HONORCODE_DB_URL=jdbc:mysql://localhost:3306/honor_code?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
HONORCODE_DB_USER=root
HONORCODE_DB_PASSWORD=root
```

If your MySQL credentials are different, set them before running the application.

On Windows PowerShell:

```powershell
$env:HONORCODE_DB_URL = "jdbc:mysql://localhost:3306/honor_code?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:HONORCODE_DB_USER = "root"
$env:HONORCODE_DB_PASSWORD = "root"
```

## 8. How to Run
Install JDK 17, Maven, MySQL, and Apache Tomcat 9. From the project root, build the WAR:

```powershell
mvn clean package
```

This generates `target\honor-code.war`. Set `HONORCODE_DB_URL`, `HONORCODE_DB_USER`, and `HONORCODE_DB_PASSWORD` in the environment used to start Tomcat. For example:

```powershell
$env:HONORCODE_DB_URL = "jdbc:mysql://localhost:3306/honor_code?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:HONORCODE_DB_USER = "your-mysql-user"
$env:HONORCODE_DB_PASSWORD = "your-mysql-password"
```

Do not commit real credentials or place them in source files.

## 9. How to Deploy on Tomcat
1. Install Apache Tomcat 9.
2. Copy the generated WAR file into Tomcat's `webapps` folder.
3. Start Tomcat.
4. Open the app in the browser:

```text
http://localhost:8080/honor-code/login
```

The login page lets you choose the student or teacher login. You can also open either login page directly:

```text
http://localhost:8080/honor-code/student-login
http://localhost:8080/honor-code/teacher-login
```

The landing page is available at `http://localhost:8080/honor-code/`, and the student assessment is available at `http://localhost:8080/honor-code/assessment` after login. Assessment results update the latest result for the selected course.

Example on Windows:

```powershell
copy target\honor-code.war "C:\path\to\apache-tomcat-9\webapps\"
```

GitHub stores and shares this project's source code; it does not run JSP/Servlet applications. A publicly accessible website also needs Java/Tomcat-capable hosting and a hosted MySQL database.

## 10. Screenshots
Add screenshots here after running the project locally.

Example placeholders:
- Login page screenshot
- Student dashboard screenshot
- Teacher dashboard screenshot
- Course page screenshot

## 11. Future Scope
- Add teacher/student profile pages
- Add grade management for results
- Add admin panel
- Add course search and filters
- Improve UI with bootstrap or templates

## Viva-Friendly Notes
This project demonstrates the following Java concepts:
- Encapsulation: private fields with getters and setters
- Inheritance: Student and Teacher extend User
- Polymorphism: common behaviour across model classes
- Interface: CrudDAO provides a generic data access contract
- Collections: List and ArrayList for data storage
- Exception handling: DatabaseException for database errors
- JDBC: DBConnection and DAO classes manage database queries with PreparedStatement
- Servlet + JSP: controllers handle requests and JSP renders the interface

## Important Note
This project is a college-level academic project and is intentionally simple and beginner-friendly. It does not use advanced frameworks, microservices, or cloud deployment.
