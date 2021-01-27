package com.charakhovich.club.web.command.impl;
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
import java.util.HashMap;


public class VerificationUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(VerificationUserCommand.class);
    private static UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        try {
            String userLogin = commandParams.get(PageParam.PARAM_USER_LOGIN);
            String verificationCode = commandParams.get(PageParam.VERIFICATION_CODE);
            boolean isApproveRegistry = userService.confirmRegistry(userLogin, verificationCode);
            Cookie cookie = isApproveRegistry ? CookieHandler.create(PageCookieName.IS_SUCCESS_REGISTRY, "true"):
                    CookieHandler.create(PageCookieName.IS_SUCCESS_REGISTRY, "false");
            resp.addCookie(cookie);
            return new Router(PagePath.REDIRECT_REGISTRY_SUCCESS_PAGE, Router.Type.REDIRECT);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
