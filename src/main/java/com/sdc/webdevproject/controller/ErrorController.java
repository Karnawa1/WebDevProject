package com.sdc.webdevproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int statusCode = (int) req.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 404:
                req.getRequestDispatcher("/WEB-INF/pages/error/404.jsp").forward(req, resp);
                break;
            case 500:
                req.getRequestDispatcher("/WEB-INF/pages/error/500.jsp").forward(req, resp);
                break;
            default:
                req.getRequestDispatcher("/WEB-INF/pages/error/500.jsp").forward(req, resp);
                break;
        }
    }
}
