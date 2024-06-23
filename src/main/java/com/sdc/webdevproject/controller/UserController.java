package com.sdc.webdevproject.controller;

import com.sdc.webdevproject.entity.User;
import com.sdc.webdevproject.exception.UserNotFoundException;
import com.sdc.webdevproject.service.UserService;
import com.sdc.webdevproject.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.tinylog.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class UserController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("GET request received at /users");
        String action = req.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                listUsers(req, resp);
            } else {
                switch (action) {
                    case "create":
                        showCreateForm(req, resp);
                        break;
                    case "edit":
                        showEditForm(req, resp);
                        break;
                    case "delete":
                        deleteUser(req, resp);
                        break;
                    case "profile":
                        showProfile(req, resp);
                        break;
                    default:
                        listUsers(req, resp);
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.error("SQLException occurred: ", ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("POST request received at /users");
        String action = req.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                listUsers(req, resp);
            } else {
                switch (action) {
                    case "create":
                        createUser(req, resp);
                        break;
                    case "edit":
                        updateUser(req, resp);
                        break;
                    default:
                        listUsers(req, resp);
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.error("SQLException occurred: ", ex);
            throw new ServletException(ex);
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Logger.debug("Listing all users");

        int page = 1;
        int recordsPerPage = 10;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        List<User> users = userService.getUsers((page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = userService.getUserCount();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        req.setAttribute("users", users);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("/WEB-INF/pages/user/list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("Showing create user form");
        req.getRequestDispatcher("/WEB-INF/pages/user/create.jsp").forward(req, resp);
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Logger.debug("Creating new user");
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setNickname(req.getParameter("nickname"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));

        userService.createUser(user);
        Logger.info("User created successfully: {}", user.getEmail());
        resp.sendRedirect(req.getContextPath() + "/users");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Logger.debug("Showing edit user form");
        Long id = Long.parseLong(req.getParameter("id"));
        try {
            User existingUser = userService.getUserById(id);
            if (existingUser == null) {
                Logger.warn("User not found for ID: {}", id);
                throw new UserNotFoundException("User not found for ID: " + id);
            }
            req.setAttribute("user", existingUser);
            req.getRequestDispatcher("/WEB-INF/pages/user/edit.jsp").forward(req, resp);
        } catch (UserNotFoundException e) {
            Logger.error("Exception occurred: ", e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Logger.debug("Updating user");
        Long id = Long.parseLong(req.getParameter("id"));
        User user = new User();
        user.setId(id);
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setNickname(req.getParameter("nickname"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));

        // Handling file upload
        Part filePart = req.getPart("avatar");
        if (filePart != null && filePart.getSize() > 0) {
            byte[] avatar = filePart.getInputStream().readAllBytes();
            user.setAvatar(avatar);
        }

        userService.updateUser(user);
        Logger.info("User updated successfully: {}", user.getEmail());
        resp.sendRedirect(req.getContextPath() + "/users?action=profile&id=" + id);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Logger.debug("Deleting user");
        Long id = Long.parseLong(req.getParameter("id"));
        userService.deleteUser(id);
        Logger.info("User deleted successfully with ID: {}", id);
        resp.sendRedirect(req.getContextPath() + "/auth?action=signout");
    }

    private void showProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.debug("Showing user profile");
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/pages/user/profile.jsp").forward(req, resp);
    }
}

