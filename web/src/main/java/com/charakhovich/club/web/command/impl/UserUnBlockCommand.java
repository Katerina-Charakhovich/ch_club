package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.Command;
import com.charakhovich.club.web.command.PageParam;
import com.charakhovich.club.web.command.PagePath;
import com.charakhovich.club.web.command.Router;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type unblock user command.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class UserUnBlockCommand implements Command {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(UserBlockCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            long userUnBlockedId = Long.parseLong(req.getParameter(PageParam.PARAM_USER_ID));
            userService.updateState(userUnBlockedId, User.State.ACTUAL);
            logger.log(Level.INFO, "The user " + userUnBlockedId + " is unblocked");
            return new Router(PagePath.REDIRECT_ADMIN_USERS, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
