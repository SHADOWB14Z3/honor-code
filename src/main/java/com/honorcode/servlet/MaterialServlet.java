package com.honorcode.servlet;

import com.honorcode.dao.MaterialDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Material;
import com.honorcode.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MaterialServlet", urlPatterns = {"/materials"})
public class MaterialServlet extends HttpServlet {
    private final MaterialDAO materialDAO = new MaterialDAO();

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
            List<Material> materials = materialDAO.findByCourseId(courseId);
            request.setAttribute("materials", materials);
            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("/materials.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course id.");
            response.sendRedirect(request.getContextPath() + "/student-dashboard");
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to display study materials.");
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
        String content = request.getParameter("content");

        if (courseIdText == null || courseIdText.trim().isEmpty() || title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Course id, title and content are required.");
            request.getRequestDispatcher("/add-material.jsp").forward(request, response);
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdText);
            Material material = new Material();
            material.setCourseId(courseId);
            material.setTitle(title.trim());
            material.setContent(content.trim());
            materialDAO.addMaterial(material);
            response.sendRedirect(request.getContextPath() + "/materials?courseId=" + courseId);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course id.");
            request.getRequestDispatcher("/add-material.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Unable to save material.");
            request.getRequestDispatcher("/add-material.jsp").forward(request, response);
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
