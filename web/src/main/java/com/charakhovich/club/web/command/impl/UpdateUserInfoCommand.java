package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.charakhovich.club.web.validation.DataValidate.isValidUserUpdate;
/**
 * The type update user info command.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class UpdateUserInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private static UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        User authUser = (User) req.getSession().getAttribute(PageAttribute.AUTH_USER);
        boolean isValidUserUpdate = isValidUserUpdate(commandParams);
        try {
            if (isValidUserUpdate) {
                authUser.setLastName(commandParams.containsKey(PageParam.PARAM_USER_LASTNAME) ?
                        commandParams.get(PageParam.PARAM_USER_LASTNAME) : authUser.getLastName());
                authUser.setFirstName(commandParams.containsKey(PageParam.PARAM_USER_FIRSTNAME) ?
                        commandParams.get(PageParam.PARAM_USER_FIRSTNAME) : authUser.getFirstName());
                authUser.setPhone(commandParams.containsKey(PageParam.PARAM_USER_PHONE) ?
                        commandParams.get(PageParam.PARAM_USER_PHONE) : authUser.getPhone());
                boolean isUpdateUser = userService.update(authUser);
                if (isUpdateUser){
                    resp.addCookie(new Cookie(PageCookieName.IS_UPDATE_USER, "true"));
                    return new Router(PagePath.REDIRECT_PRIVATE_CABINET_PAGE, Router.Type.REDIRECT);
                }else
                    req.setAttribute(PageAttribute.IS_UPDATE_USER,"false");
            } else {
                req.setAttribute(PageAttribute.MAP_PAGE_PARAMETERS, commandParams);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(PagePath.PRIVATE_CABINET, Router.Type.FORWARD);
    }
}
