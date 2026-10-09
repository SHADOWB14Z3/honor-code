package com.honorcode.servlet;

import com.honorcode.dao.CourseDAO;
import com.honorcode.dao.EnrollmentDAO;
import com.honorcode.dao.ResultDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Course;
import com.honorcode.model.Enrollment;
import com.honorcode.model.Result;
import com.honorcode.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CourseServlet", urlPatterns = {"/courses", "/teacher-dashboard", "/student-dashboard"})
public class CourseServlet extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final ResultDAO resultDAO = new ResultDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            if ("TEACHER".equalsIgnoreCase(user.getRole())) {
                List<Course> courses = courseDAO.findByTeacherId(user.getId());
                request.setAttribute("courses", courses);
                request.getRequestDispatcher("/teacher-dashboard.jsp").forward(request, response);
            } else {
                List<Course> courses = courseDAO.findAvailableCoursesForStudent(user.getId());
                List<Enrollment> enrollments = enrollmentDAO.findByStudentId(user.getId());
                List<Result> results = resultDAO.findByStudentId(user.getId());
                double averageScore = 0;
                for (Result result : results) {
                    averageScore += result.getScore();
                }
                if (!results.isEmpty()) {
                    averageScore /= results.size();
                }
                request.setAttribute("courses", courses);
                request.setAttribute("enrollments", enrollments);
                request.setAttribute("results", results);
                request.setAttribute("averageScore", averageScore);
                request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to load courses.");
            if ("TEACHER".equalsIgnoreCase(user.getRole())) {
                request.getRequestDispatcher("/teacher-dashboard.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!"TEACHER".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");

        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Course title and description are required.");
            request.getRequestDispatcher("/add-course.jsp").forward(request, response);
            return;
        }

        try {
            Course newCourse = new Course(title.trim(), description.trim(), user.getId());
            courseDAO.addCourse(newCourse);
            response.sendRedirect(request.getContextPath() + "/teacher-dashboard");
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Could not create the course right now.");
            request.getRequestDispatcher("/add-course.jsp").forward(request, response);
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
