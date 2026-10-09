package com.honorcode.servlet;

import com.honorcode.dao.AssignmentDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Assignment;
import com.honorcode.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AssignmentServlet", urlPatterns = {"/assignments"})
public class AssignmentServlet extends HttpServlet {
    private final AssignmentDAO assignmentDAO = new AssignmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null) {
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
            List<Assignment> assignments = assignmentDAO.findByCourseId(courseId);
            request.setAttribute("assignments", assignments);
            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("/assignments.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course id.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to list assignments.");
            request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null || !"TEACHER".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String courseIdText = request.getParameter("courseId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");

        if (courseIdText == null || courseIdText.trim().isEmpty() || title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty() || dueDate == null || dueDate.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All assignment fields are required.");
            request.getRequestDispatcher("/add-assignment.jsp").forward(request, response);
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdText);
            Assignment assignment = new Assignment();
            assignment.setCourseId(courseId);
            assignment.setTitle(title.trim());
            assignment.setDescription(description.trim());
            assignment.setDueDate(dueDate.trim());
            assignmentDAO.addAssignment(assignment);
            response.sendRedirect(request.getContextPath() + "/assignments?courseId=" + courseId);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course id.");
            request.getRequestDispatcher("/add-assignment.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to create assignment.");
            request.getRequestDispatcher("/add-assignment.jsp").forward(request, response);
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
