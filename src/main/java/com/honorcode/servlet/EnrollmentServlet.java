package com.honorcode.servlet;

import com.honorcode.dao.CourseDAO;
import com.honorcode.dao.EnrollmentDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Course;
import com.honorcode.model.Enrollment;
import com.honorcode.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EnrollmentServlet", urlPatterns = {"/enroll"})
public class EnrollmentServlet extends HttpServlet {
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null || !"STUDENT".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Enrollment> enrollments = enrollmentDAO.findByStudentId(user.getId());
            List<Course> availableCourses = courseDAO.findAvailableCoursesForStudent(user.getId());
            request.setAttribute("enrolledCourses", enrollments);
            request.setAttribute("availableCourses", availableCourses);
            request.getRequestDispatcher("/enrolled-courses.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to load enrollments.");
            request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null || !"STUDENT".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String courseIdText = request.getParameter("courseId");
        if (courseIdText == null || courseIdText.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Invalid course id.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdText);
            if (!enrollmentDAO.enrollStudent(user.getId(), courseId)) {
                request.setAttribute("errorMessage", "You are already enrolled in this course.");
            }
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course id.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to complete the enrollment.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
        }
    }

    private User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("user");
    }
}
