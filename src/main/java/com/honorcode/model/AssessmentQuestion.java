package com.honorcode.model;

import java.util.List;

public class AssessmentQuestion {
    private final int number;
    private final String question;
    private final List<String> options;

    public AssessmentQuestion(int number, String question, List<String> options) {
        this.number = number;
        this.question = question;
        this.options = options;
    }

    public int getNumber() {
        return number;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }
}
