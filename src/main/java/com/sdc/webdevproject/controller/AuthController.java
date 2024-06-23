package com.sdc.webdevproject.controller;

import com.sdc.webdevproject.entity.User;
import com.sdc.webdevproject.exception.AuthException;
import com.sdc.webdevproject.service.AuthService;
import com.sdc.webdevproject.service.impl.AuthServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tinylog.Logger;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("GET request received at /auth");
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            showSignInForm(req, resp);
        } else {
            switch (action) {
                case "signin":
                    showSignInForm(req, resp);
                    break;
                case "signup":
                    showSignUpForm(req, resp);
                    break;
                case "signout":
                    signOut(req, resp);
                    break;
                case "confirm":
                    confirmUser(req, resp);
                    break;
                default:
                    showSignInForm(req, resp);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("POST request received at /auth");
        String action = req.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                showSignInForm(req, resp);
            } else {
                switch (action) {
                    case "signin":
                        signIn(req, resp);
                        break;
                    case "signup":
                        signUp(req, resp);
                        break;
                    default:
                        showSignInForm(req, resp);
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.error("SQLException occurred: ", ex);
            throw new ServletException(ex);
        }
    }

    private void showSignInForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("Showing sign-in form");
        req.getRequestDispatcher("/WEB-INF/pages/auth/signin.jsp").forward(req, resp);
    }

    private void showSignUpForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("Showing sign-up form");
        req.getRequestDispatcher("/WEB-INF/pages/auth/signup.jsp").forward(req, resp);
    }

    private void signIn(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Logger.debug("Processing sign-in");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = authService.signIn(email, password);
        if (user != null) {
            Logger.info("User signed in successfully: {}", email);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/users");
        } else {
            Logger.warn("Invalid email or password for email: {}", email);
            req.setAttribute("errorMessage", "Invalid email or password.");
            req.getRequestDispatcher("/WEB-INF/pages/auth/signin.jsp").forward(req, resp);
        }
    }

    private void signUp(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Logger.debug("Processing sign-up");
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setNickname(req.getParameter("nickname"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));

        authService.signUp(user);
        Logger.info("User signed up successfully: {}", user.getEmail());
        resp.sendRedirect(req.getContextPath() + "/auth?action=signin");
    }

    private void signOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Logger.debug("Processing sign-out");
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/auth?action=signin");
    }

    private void confirmUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("Processing user confirmation");
        String token = req.getParameter("token");
        try {
            authService.confirmUser(token);
            req.setAttribute("message", "Your account has been successfully confirmed. You can now sign in.");
            req.getRequestDispatcher("/WEB-INF/pages/auth/signin.jsp").forward(req, resp);
        } catch (SQLException | IllegalArgumentException ex) {
            Logger.error("User confirmation failed: ", ex);
            req.setAttribute("errorMessage", "Invalid or expired confirmation link.");
            req.getRequestDispatcher("/WEB-INF/pages/auth/error.jsp").forward(req, resp);
        }
    }
}
