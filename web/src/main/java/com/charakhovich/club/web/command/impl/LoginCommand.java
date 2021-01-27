package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.model.util.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter(PageAttribute.USER_LOGIN);
        HttpSession session = req.getSession();
        String password = req.getParameter(PageAttribute.USER_PASSWORD);

        try {
            if (userService.checkLogin(login, password)) {
                Optional<User> authUser = userService.findUserByLogin(login);
                switch (authUser.get().getState()) {
                    case UNCONFIRMED:
                        req.setAttribute(PageAttribute.IS_UNCONFIRMED_USER, "true");
                        break;
                    case BLOCKED:
                        req.setAttribute(PageAttribute.IS_BLOCKED_USER, "true");
                        break;
                    case ACTUAL:
                        session.setAttribute(PageAttribute.AUTH_USER, authUser.get());
                        return authUser.get().getRole() == User.Role.ADMIN ?
                                new Router(PagePath.REDIRECT_PRIVATE_CABINET_PAGE, Router.Type.REDIRECT) :
                                new Router(PagePath.REDIRECT_MAIN, Router.Type.REDIRECT);
                    default:
                }
            } else {
                req.setAttribute(PageAttribute.IS_INVALID_USER, "true");
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        String currentCommand = (String) session.getAttribute(PageAttribute.CURRENT_COMMAND);
        if (currentCommand == null) {
            currentCommand = CommandType.MAIN_PAGE_VIEW.name();
        }
        StringBuilder page = new StringBuilder().append(ApplicationParam.CONTROLLER_REGEX).append(currentCommand);
        return new Router(page.toString(), Router.Type.FORWARD);
    }
}
