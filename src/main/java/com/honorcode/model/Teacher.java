package com.honorcode.model;

// Teacher is a specialized user type for instructors.
// It extends User and demonstrates inheritance clearly.
public class Teacher extends User {
    public Teacher() {
        super();
        setRole("TEACHER");
    }

    public Teacher(String username, String password, String fullName, String email) {
        super(username, password, fullName, email, "TEACHER");
    }
}
