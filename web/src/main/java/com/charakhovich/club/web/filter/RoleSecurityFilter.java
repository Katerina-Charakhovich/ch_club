package com.charakhovich.club.web.filter;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.command.factory.ActionFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Role security filter.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
@WebFilter(filterName = "RoleSecurityFilter", urlPatterns = {"/do"})
public class RoleSecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(RoleSecurityFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        CommandType commandType = ActionFactory.defineCommandType(request);
        Optional<User> userOptional = Optional.empty();
        if (session.getAttribute(PageAttribute.AUTH_USER) != null) {
            User user = (User) session.getAttribute(PageAttribute.AUTH_USER);
            userOptional = Optional.of(user);
        }
        User.Role userRole = userOptional.isPresent() ? userOptional.get().getRole() : User.Role.GUEST;
        Set<CommandType> commands = new HashSet<>();
        switch (userRole) {
            case USER:
                commands = RolePermission.USER.getRoleCommands();
                break;
            case ADMIN:
                commands = RolePermission.ADMIN.getRoleCommands();
                break;
            case GUEST:
                commands = RolePermission.GUEST.getRoleCommands();
                break;
        }
        if (!commands.contains(CommandType.valueOf(commandType.toString().toUpperCase()))) {
            logger.log(Level.ERROR, "Role {} has no access to {} command", userRole, commandType);
            response.sendRedirect(request.getContextPath() + PagePath.REDIRECT_ERROR_403);
            return;
        }
        chain.doFilter(req, resp);
    }
}
