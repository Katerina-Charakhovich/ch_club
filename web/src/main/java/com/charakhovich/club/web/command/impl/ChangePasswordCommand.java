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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.charakhovich.club.web.validation.DataValidate.isValidUserUpdate;
/**
 * The type Change photo command.
 * This command allows to change password in private cabinet
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class ChangePasswordCommand implements Command {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        User authUser = (User) req.getSession().getAttribute(PageAttribute.AUTH_USER);
        boolean isValidCDataForChangePassword = isValidUserUpdate(commandParams);
        try {

            if (isValidCDataForChangePassword) {
                String oldPassword = req.getParameter(PageParam.OLD_PASSWORD);
                String newPassword = req.getParameter(PageParam.NEW_PASSWORD);
                String repeatedNewPassword = req.getParameter(PageParam.REPEATED_NEW_PASSWORD);
                if (userService.checkLogin(authUser.getLogin(), oldPassword)) {
                    if (newPassword.equals(repeatedNewPassword)) {
                        String hashNewPassword =Encryptor.hashPassword(newPassword);
                        boolean result=userService.updatePassword(authUser.getUserId(), hashNewPassword);
                        resp.addCookie(new Cookie(PageCookieName.IS_CORRECT_CHANGE_PASSWORD, "true"));
                        return new Router(PagePath.REDIRECT_PRIVATE_CABINET_PAGE, Router.Type.REDIRECT);
                    } else {
                        req.setAttribute(PageAttribute.IS_NONEQUIVALENT_PASSWORD, "true");
                        req.setAttribute(PageAttribute.IS_CORRECT_CHANGE_PASSWORD, "false");
                    }
                } else {
                    req.setAttribute(PageAttribute.IS_CORRECT_CHANGE_PASSWORD, "false");
                    req.setAttribute(PageAttribute.IS_INCORRECT_OLD_PASSWORD, "true");
                }

            } else {
                req.setAttribute(PageAttribute.IS_CORRECT_CHANGE_PASSWORD, "false");
                req.setAttribute(PageAttribute.MAP_PAGE_PARAMETERS, commandParams);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(PagePath.PRIVATE_CABINET, Router.Type.FORWARD);
    }
}
