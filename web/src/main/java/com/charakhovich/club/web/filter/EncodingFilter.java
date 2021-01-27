package com.charakhovich.club.web.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",
        urlPatterns = "/*")
public class EncodingFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    private static final Logger logger = LogManager.getLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig config) {
        logger.log(Level.INFO,"Filter init() start");
        ServletContext context = config.getServletContext();
        FilterRegistration registration1 = context.getFilterRegistration("PageSecurityFilter");
        registration1.addMappingForServletNames(null, true, "/do");
        registration1.addMappingForUrlPatterns(null, true, "/jsp/*");
        FilterRegistration registration2 = context.getFilterRegistration("RoleSecurityFilter");
        registration2.addMappingForServletNames(null, true, "/do");
        registration2.addMappingForUrlPatterns(null, true, "/do/*");
    }

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain filterChain) throws IOException, ServletException {
        logger.log(Level.INFO, "EncodingFilter start set UTF8");
        rq.setCharacterEncoding(ENCODING);
        filterChain.doFilter(rq, rs);

    }
}
