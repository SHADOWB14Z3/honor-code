package com.honorcode.servlet;

import com.honorcode.dao.AssignmentDAO;
import com.honorcode.dao.SubmissionDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Assignment;
import com.honorcode.model.Submission;
import com.honorcode.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SubmissionServlet", urlPatterns = {"/submission"})
public class SubmissionServlet extends HttpServlet {
    private final AssignmentDAO assignmentDAO = new AssignmentDAO();
    private final SubmissionDAO submissionDAO = new SubmissionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String assignmentIdText = request.getParameter("assignmentId");
        if (assignmentIdText == null || assignmentIdText.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Invalid assignment id.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
            return;
        }

        try {
            int assignmentId = Integer.parseInt(assignmentIdText);
            Assignment assignment = assignmentDAO.findById(assignmentId);
            if (assignment == null) {
                request.setAttribute("errorMessage", "Invalid assignment id.");
                response.sendRedirect(request.getContextPath() + "/student-dashboard");
                return;
            }

            List<Submission> submissions = submissionDAO.findByAssignmentId(assignmentId);
            Submission currentSubmission = submissionDAO.findByAssignmentAndStudent(assignmentId, user.getId());
            request.setAttribute("assignment", assignment);
            request.setAttribute("submissions", submissions);
            request.setAttribute("currentSubmission", currentSubmission);
            request.getRequestDispatcher("/submission.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid assignment id.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to load assignment submission page.");
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

        String assignmentIdText = request.getParameter("assignmentId");
        String content = request.getParameter("content");

        if (assignmentIdText == null || assignmentIdText.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Assignment id and content are required.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
            return;
        }

        try {
            int assignmentId = Integer.parseInt(assignmentIdText);
            Submission submission = new Submission();
            submission.setAssignmentId(assignmentId);
            submission.setStudentId(user.getId());
            submission.setContent(content.trim());
            submissionDAO.submitAssignment(submission);
            response.sendRedirect(request.getContextPath() + "/submission?assignmentId=" + assignmentId);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid assignment id.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to submit assignment.");
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
