package com.honorcode.servlet;

import com.honorcode.dao.UserDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Student;
import com.honorcode.model.Teacher;
import com.honorcode.model.User;
import com.honorcode.util.PasswordUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (isBlank(fullName) || isBlank(email) || isBlank(username) || isBlank(password) || isBlank(role)) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            if (userDAO.isUsernameTaken(username.trim())) {
                request.setAttribute("errorMessage", "That username is already taken.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            User user;
            if ("TEACHER".equalsIgnoreCase(role)) {
                user = new Teacher(username.trim(), PasswordUtil.hashPassword(password), fullName.trim(), email.trim());
            } else {
                user = new Student(username.trim(), PasswordUtil.hashPassword(password), fullName.trim(), email.trim());
            }

            userDAO.registerUser(user);
            request.setAttribute("successMessage", "Registration successful. Please login.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Database error while registering a user.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
