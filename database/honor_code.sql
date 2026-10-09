CREATE DATABASE IF NOT EXISTS honor_code;
USE honor_code;

DROP TABLE IF EXISTS results;
DROP TABLE IF EXISTS submissions;
DROP TABLE IF EXISTS assignments;
DROP TABLE IF EXISTS materials;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    teacher_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_courses_teacher FOREIGN KEY (teacher_id) REFERENCES users(id)
);

CREATE TABLE enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (student_id, course_id),
    CONSTRAINT fk_enrollments_student FOREIGN KEY (student_id) REFERENCES users(id),
    CONSTRAINT fk_enrollments_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE materials (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_materials_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE assignments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    due_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assignments_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE submissions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    assignment_id INT NOT NULL,
    student_id INT NOT NULL,
    content TEXT NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (assignment_id, student_id),
    CONSTRAINT fk_submissions_assignment FOREIGN KEY (assignment_id) REFERENCES assignments(id),
    CONSTRAINT fk_submissions_student FOREIGN KEY (student_id) REFERENCES users(id)
);

CREATE TABLE results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    score DOUBLE NOT NULL,
    grade VARCHAR(10) NOT NULL,
    remarks TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (student_id, course_id),
    CONSTRAINT fk_results_student FOREIGN KEY (student_id) REFERENCES users(id),
    CONSTRAINT fk_results_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

INSERT INTO users (username, password, full_name, email, role) VALUES
('teacher1', 'cde383eee8ee7a4400adf7a15f716f179a2eb97646b37e089eb8d6d04e663416', 'Dr. Aisha Khan', 'teacher1@honorcode.edu', 'TEACHER'),
('student1', '703b0a3d6ad75b649a28adde7d83c6251da457549263bc7ff45ec709b0a8448b', 'Ali Hassan', 'student1@honorcode.edu', 'STUDENT');

INSERT INTO courses (title, description, teacher_id) VALUES
('Data Structures', 'Learn arrays, linked lists, stacks and queues.', 1),
('Object Oriented Programming', 'Understand Java classes, objects and inheritance.', 1);

INSERT INTO enrollments (student_id, course_id) VALUES
(2, 1),
(2, 2);

INSERT INTO materials (course_id, title, content) VALUES
(1, 'Introduction to Arrays', 'Arrays store data in contiguous memory locations.'),
(2, 'Java Class Basics', 'A class is a blueprint for creating objects.');

INSERT INTO assignments (course_id, title, description, due_date) VALUES
(1, 'Array Practice', 'Solve 10 array problems and explain complexity.', '2026-10-15'),
(2, 'OOP Reflection', 'Write a short note on encapsulation and inheritance.', '2026-10-18');

INSERT INTO submissions (assignment_id, student_id, content) VALUES
(1, 2, 'I solved all array tasks and explained time complexity clearly.');

INSERT INTO results (student_id, course_id, score, grade, remarks) VALUES
(2, 1, 88.5, 'A', 'Strong work in data structure concepts.');
