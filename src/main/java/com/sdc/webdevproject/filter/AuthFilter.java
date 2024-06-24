package com.sdc.webdevproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final List<String> ALLOWED_PATHS = Arrays.asList(
            "/css/", "/js/", "/images/", "/auth", "/auth/signup", "/auth?action=signup"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Logger.debug("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean allowedPath = ALLOWED_PATHS.stream().anyMatch(path::startsWith);

        if (loggedIn || allowedPath) {
            Logger.debug("User is logged in or accessing allowed path, proceeding with request");
            chain.doFilter(request, response);
        } else {
            Logger.warn("User is not logged in, redirecting to login page");
            resp.sendRedirect(req.getContextPath() + "/auth?action=signin");
        }
    }

    @Override
    public void destroy() {
        Logger.debug("AuthFilter destroyed");
    }
}
