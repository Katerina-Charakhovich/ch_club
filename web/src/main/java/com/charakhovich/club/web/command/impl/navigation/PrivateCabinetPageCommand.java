package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type "Private cabinet" page command.
 * This command prepares data for "Private cabinet" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class PrivateCabinetPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PrivateCabinetPageCommand.class);
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (PageCookieName.IS_CORRECT_CHANGE_PASSWORD.equals(cookie.getName())) {
                    req.setAttribute(PageAttribute.IS_CORRECT_CHANGE_PASSWORD, cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
                if (PageCookieName.IS_UPDATE_USER.equals(cookie.getName())) {
                    req.setAttribute(PageAttribute.IS_UPDATE_USER, cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
            }
        }
        try {
            User authUser = (User) req.getSession().getAttribute(PageAttribute.AUTH_USER);
            authUser.setListTicket(userService.findUserTickets(authUser.getUserId()));
            req.getSession().setAttribute(PageAttribute.AUTH_USER, authUser);
            req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.PRIVATE_CABINET.toString());
            return new Router(PagePath.PRIVATE_CABINET, Router.Type.FORWARD);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}

