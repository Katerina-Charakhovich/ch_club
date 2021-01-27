package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class AdminUsersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AdminUsersPageCommand.class);
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        int currentPaginationPage = Integer.parseInt(Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE))
                .orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER)));

        try {
            List<User> listUser = userService.findByRole(User.Role.USER,
                    new Page(currentPaginationPage, ApplicationParam.DEFAULT_COUNT_EVENT_FOR_VIEW));
            int countOfUser = userService.countByRole(User.Role.USER);
            int countPages = (int) Math.ceil(countOfUser * 1.0 / Page.RECORD_NUMBER);
            req.setAttribute(PageAttribute.LIST_USERS, listUser);
            req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, currentPaginationPage);
            req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
            HttpSession session = req.getSession();
            session.setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.ADMIN_USERS.toString());
            logger.log(Level.INFO, "The admin went to the page AdminUsers");
            return new Router(PagePath.ADMIN_USERS, Router.Type.FORWARD);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
