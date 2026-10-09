package com.honorcode.servlet;

import com.honorcode.dao.EnrollmentDAO;
import com.honorcode.dao.ResultDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.AssessmentQuestion;
import com.honorcode.model.Enrollment;
import com.honorcode.model.Result;
import com.honorcode.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// AssessmentServlet shows Java quiz questions and saves the student's score through JDBC.
@WebServlet(name = "AssessmentServlet", urlPatterns = {"/assessment"})
public class AssessmentServlet extends HttpServlet {
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final ResultDAO resultDAO = new ResultDAO();
    private final List<AssessmentQuestion> questions = createQuestions();
    private final int[] correctAnswers = {2, 1, 2, 0, 1, 0, 1, 2, 2, 1};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/student-login");
            return;
        }
        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only students can take this assessment.");
            return;
        }

        try {
            prepareAssessmentPage(request, enrollmentDAO.findByStudentId(user.getId()));
            HttpSession session = request.getSession();
            if ("true".equals(request.getParameter("completed"))) {
                request.setAttribute("assessmentScore", session.getAttribute("assessmentScore"));
                request.setAttribute("assessmentCorrect", session.getAttribute("assessmentCorrect"));
                request.setAttribute("assessmentCourse", session.getAttribute("assessmentCourse"));
                session.removeAttribute("assessmentScore");
                session.removeAttribute("assessmentCorrect");
                session.removeAttribute("assessmentCourse");
            }
            request.getRequestDispatcher("/assessment.jsp").forward(request, response);
        } catch (DatabaseException e) {
            getServletContext().log("Unable to prepare the student assessment page.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load assessment data. Please try again.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/student-login");
            return;
        }
        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only students can submit this assessment.");
            return;
        }

        List<Enrollment> enrollments;
        try {
            enrollments = enrollmentDAO.findByStudentId(user.getId());
        } catch (DatabaseException e) {
            getServletContext().log("Unable to load student enrollments for assessment.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to verify course enrollment. Please try again.");
            return;
        }

        int courseId;
        try {
            courseId = Integer.parseInt(request.getParameter("courseId"));
        } catch (NumberFormatException e) {
            prepareAssessmentPage(request, enrollments);
            request.setAttribute("errorMessage", "Choose a valid enrolled course before submitting.");
            request.getRequestDispatcher("/assessment.jsp").forward(request, response);
            return;
        }

        Enrollment selectedEnrollment = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                selectedEnrollment = enrollment;
                break;
            }
        }
        if (selectedEnrollment == null) {
            prepareAssessmentPage(request, enrollments);
            request.setAttribute("errorMessage", "You can only take an assessment for a course you are enrolled in.");
            request.getRequestDispatcher("/assessment.jsp").forward(request, response);
            return;
        }

        int correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            String answer = request.getParameter("answer_" + i);
            if (answer == null || answer.isEmpty()) {
                continue;
            }
            try {
                int selectedAnswer = Integer.parseInt(answer);
                if (selectedAnswer < 0 || selectedAnswer >= questions.get(i).getOptions().size()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid answer selection.");
                    return;
                }
                if (selectedAnswer == correctAnswers[i]) {
                    correctCount++;
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid answer selection.");
                return;
            }
        }

        double score = Math.round(correctCount * 100.0 / questions.size());
        Result result = new Result();
        result.setStudentId(user.getId());
        result.setCourseId(courseId);
        result.setScore(score);
        result.setGrade(getGrade(score));
        result.setRemarks("Java fundamentals assessment: " + correctCount + " of " + questions.size() + " correct.");

        try {
            resultDAO.saveResult(result);
            HttpSession session = request.getSession();
            session.setAttribute("assessmentScore", score);
            session.setAttribute("assessmentCorrect", correctCount);
            session.setAttribute("assessmentCourse", selectedEnrollment.getCourseTitle());
            response.sendRedirect(request.getContextPath() + "/assessment?completed=true");
        } catch (DatabaseException e) {
            getServletContext().log("Unable to save student assessment result.", e);
            prepareAssessmentPage(request, enrollments);
            request.setAttribute("errorMessage", "Your result could not be saved. Please try again.");
            request.getRequestDispatcher("/assessment.jsp").forward(request, response);
        }
    }

    private void prepareAssessmentPage(HttpServletRequest request, List<Enrollment> enrollments) {
        request.setAttribute("questions", questions);
        request.setAttribute("enrolledCourses", enrollments);
    }

    private User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("user");
    }

    private String getGrade(double score) {
        if (score >= 90) {
            return "A";
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "F";
    }

    private List<AssessmentQuestion> createQuestions() {
        List<AssessmentQuestion> questionList = new ArrayList<>();
        questionList.add(new AssessmentQuestion(1,
                "Which OOP principle allows an object to take different forms?",
                List.of("Encapsulation", "Inheritance", "Polymorphism", "Abstraction")));
        questionList.add(new AssessmentQuestion(2,
                "Which keyword prevents a class from being subclassed in Java?",
                List.of("static", "final", "private", "sealed")));
        questionList.add(new AssessmentQuestion(3,
                "What is the time complexity of accessing an element in an ArrayList?",
                List.of("O(log n)", "O(n)", "O(1)", "O(n log n)")));
        questionList.add(new AssessmentQuestion(4,
                "Which interface must a class implement to be used in a for-each loop?",
                List.of("Iterable", "Iterator", "Comparable", "Serializable")));
        questionList.add(new AssessmentQuestion(5,
                "What does the volatile keyword guarantee in Java?",
                List.of("Thread safety", "Visibility across threads", "Atomic operations", "Mutual exclusion")));
        questionList.add(new AssessmentQuestion(6,
                "Which design pattern does Collections.sort() use internally?",
                List.of("Strategy", "Observer", "Factory", "Singleton")));
        questionList.add(new AssessmentQuestion(7,
                "What is the output of System.out.println(10 / 3) in Java?",
                List.of("3.33", "3", "3.0", "Compile error")));
        questionList.add(new AssessmentQuestion(8,
                "Which exception is thrown when you access an array with an invalid index?",
                List.of("NullPointerException", "IllegalArgumentException",
                        "ArrayIndexOutOfBoundsException", "ClassCastException")));
        questionList.add(new AssessmentQuestion(9,
                "What does HashMap.put() return if the key already exists?",
                List.of("null", "The new value", "The old value", "false")));
        questionList.add(new AssessmentQuestion(10,
                "Which Java 8 feature allows passing behavior as a parameter?",
                List.of("Generics", "Lambda expressions", "Annotations", "Reflection")));
        return questionList;
    }
}
