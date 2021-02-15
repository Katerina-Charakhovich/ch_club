package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.Page;
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
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The type "Users" page command.
 * This command prepares data for "Users" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AdminUsersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AdminUsersPageCommand.class);
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Optional<String> numberPageOptional = Optional.empty();
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(PageCookieName.IS_USER_ADD_BALANCE)) {
                    req.setAttribute(PageAttribute.IS_USER_ADD_BALANCE, cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
                if (cookie.getName().equals(PageCookieName.IS_AMOUNT_VALID)) {
                    req.setAttribute(PageAttribute.IS_AMOUNT_VALID, cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
            }
        }
        int numberPage = Integer.parseInt(Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE))
                .orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER)));
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        String subLastName = req.getParameter(PageParam.PARAM_SUBSTRING_LASTNAME);
        try {
            int countOfUser;
            int countPages;
            List<User> listUser;
            StringBuilder currentCommand;
            if (subLastName==null) {
                countOfUser = userService.countByRole(User.Role.USER);
                countPages = (int) Math.ceil(countOfUser * 1.0 / Page.RECORD_NUMBER);
                numberPage = numberPage > countPages ? countPages : numberPage < 1 ? ApplicationParam.DEFAULT_PAGINATION_NUMBER : numberPage;
                listUser = userService.findByRole(User.Role.USER,
                        new Page(numberPage, ApplicationParam.DEFAULT_COUNT_EVENT_FOR_VIEW));
                currentCommand = new StringBuilder(CommandType.ADMIN_USERS.toString());
            } else {
                countOfUser = userService.countByRoleByLastname(User.Role.USER, subLastName);
                countPages = (int) Math.ceil(countOfUser * 1.0 / Page.RECORD_NUMBER);
                numberPage=numberPage>countPages&&countPages!=0?countPages:numberPage<1?
                        ApplicationParam.DEFAULT_PAGINATION_NUMBER:numberPage;
                numberPage = numberPage > countPages && countPages!=0 ? countPages : numberPage < 1 ?
                        ApplicationParam.DEFAULT_PAGINATION_NUMBER : numberPage;
                    listUser = userService.findByRoleByLastname(User.Role.USER, subLastName,
                        new Page(numberPage, ApplicationParam.DEFAULT_COUNT_EVENT_FOR_VIEW));
              /* currentCommand = new StringBuilder(CommandType.ADMIN_USERS.toString());*/
                 currentCommand = new StringBuilder(CommandType.ADMIN_USERS.toString()).
                        append(ApplicationParam.ONE_PARAMETER_SPLIT).append(PageParam.PARAM_SUBSTRING_LASTNAME).
                        append(ApplicationParam.SIGN_EQUALS).append(subLastName);
            }
            req.setAttribute(PageParam.PARAM_SUBSTRING_LASTNAME, subLastName);
            req.setAttribute(PageAttribute.LIST_USERS, listUser);
            req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, numberPage);
            req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
            HttpSession session = req.getSession();

            session.setAttribute(PageAttribute.CURRENT_COMMAND, currentCommand.toString());
            logger.log(Level.INFO, "The admin went to the page AdminUsers");
            return new Router(PagePath.ADMIN_USERS, Router.Type.FORWARD);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
