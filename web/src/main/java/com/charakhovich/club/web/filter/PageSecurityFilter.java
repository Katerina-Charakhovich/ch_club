package com.charakhovich.club.web.filter;

import com.charakhovich.club.web.command.PagePath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Page security filter.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
@WebFilter(filterName = "PageSecurityFilter", urlPatterns = {"/jsp/*"})
public class PageSecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.sendRedirect(request.getContextPath() + PagePath.REDIRECT_MAIN);
    }
}
