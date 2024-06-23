package com.sdc.webdevproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class LocalizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Logger.debug("LocalizationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String lang = req.getParameter("lang");
        if (lang != null) {
            Locale locale = new Locale(lang);
            req.getSession().setAttribute("locale", locale);
            Logger.debug("Locale set to: {}", locale);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Logger.debug("LocalizationFilter destroyed");
    }
}
