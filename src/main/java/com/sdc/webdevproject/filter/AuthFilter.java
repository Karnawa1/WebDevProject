package com.sdc.webdevproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tinylog.Logger;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Logger.debug("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/auth";
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(loginURI + "/signup");

        if (loggedIn || loginRequest || req.getMethod().equalsIgnoreCase("POST")) {
            Logger.debug("User is logged in or accessing login page, proceeding with request");
            chain.doFilter(request, response);
        } else {
            Logger.warn("User is not logged in, redirecting to login page");
            resp.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
        Logger.debug("AuthFilter destroyed");
    }
}
