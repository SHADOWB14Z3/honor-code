package com.honorcode.servlet;

// LoginServlet handles the login form and starts the user session.
// It shows how Java Servlets receive HTTP requests and redirect users by role.
import com.honorcode.dao.UserDAO;
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.User;
import com.honorcode.util.PasswordUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/student-login", "/teacher-login"})
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(getLoginPage(request)).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String requiredRole = getRequiredRole(request);

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password are required.");
            request.getRequestDispatcher(getLoginPage(request)).forward(request, response);
            return;
        }

        try {
            User user = userDAO.findByUsername(username.trim());
            if (user == null
                    || !PasswordUtil.hashPassword(password).equals(user.getPassword())
                    || (requiredRole != null && !requiredRole.equalsIgnoreCase(user.getRole()))) {
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher(getLoginPage(request)).forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if ("TEACHER".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/teacher-dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/student-dashboard");
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", "Database error while logging in.");
            request.getRequestDispatcher(getLoginPage(request)).forward(request, response);
        }
    }

    private String getLoginPage(HttpServletRequest request) {
        if ("/student-login".equals(request.getServletPath())) {
            return "/student-login.jsp";
        }
        if ("/teacher-login".equals(request.getServletPath())) {
            return "/teacher-login.jsp";
        }
        return "/login.jsp";
    }

    private String getRequiredRole(HttpServletRequest request) {
        if ("/student-login".equals(request.getServletPath())) {
            return "STUDENT";
        }
        if ("/teacher-login".equals(request.getServletPath())) {
            return "TEACHER";
        }
        return null;
    }
}
