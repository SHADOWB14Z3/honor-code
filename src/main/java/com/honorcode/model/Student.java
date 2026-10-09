package com.honorcode.model;

// Student is a specialized user type for learners.
// It exists to show inheritance in a simple and clear way.
public class Student extends User {
    public Student() {
        super();
        setRole("STUDENT");
    }

    public Student(String username, String password, String fullName, String email) {
        super(username, password, fullName, email, "STUDENT");
    }
}
