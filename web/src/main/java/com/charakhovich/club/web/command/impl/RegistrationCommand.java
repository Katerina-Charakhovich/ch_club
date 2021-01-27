package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.MailSender;
import com.charakhovich.club.web.util.TokenGenerate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;

import static com.charakhovich.club.web.validation.DataValidate.isValidUserNew;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private static UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {

        String page = "";
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        try {
            boolean isValidUserNew =isValidUserNew(commandParams);
            if (isValidUserNew){

            }
            if (isValidUserNew(commandParams)) {
                String userLogin = commandParams.get(PageParam.PARAM_USER_LOGIN);
                String userLastName = commandParams.get(PageParam.PARAM_USER_LASTNAME);
                String userFirstName = commandParams.get(PageParam.PARAM_USER_FIRSTNAME);
                String userPhone = commandParams.get(PageParam.PARAM_USER_PHONE);
                String userPassword = commandParams.get(PageParam.PARAM_USER_PASSWORD);
                String userRepeatedPassword = commandParams.get(PageParam.PARAM_USER_REPEATED_PASSWORD);
                if (!userPassword.equals(userRepeatedPassword)){
                    req.setAttribute(PageAttribute.IS_NONEQUIVALENT_PASSWORD, "true");
                    page = PagePath.REGISTRY_PAGE;
                    return new Router(page, Router.Type.FORWARD);
                }
                User user = new User(userFirstName, userLastName, userPhone, userLogin, User.Role.USER, User.State.UNCONFIRMED);
                String token = TokenGenerate.generateString(20);
                Optional<User> userRegistry = userService.registry(user, userPassword, token);
                if (userRegistry.isPresent()) {
                    StringBuilder handlerRegistration = new StringBuilder("http://localhost:8080/club/do/verification_token?email=")
                            .append(userRegistry.get().getLogin()).append("&").append("code=").append(token);
                    boolean isSend = MailSender.getINSTANCE().sendMessage(user.getLogin(), "Registration", handlerRegistration.toString());
                    if (!isSend) {
                        userService.delete(userRegistry.get().getUserId());
                        resp.addCookie(new Cookie(PageCookieName.PRE_REGISTRY, "false"));
                    } else {
                        resp.addCookie(new Cookie(PageCookieName.PRE_REGISTRY, "true"));
                    }
                    return new Router(PagePath.REDIRECT_REGISTRY_SUCCESS_PAGE, Router.Type.REDIRECT);
                } else {
                    req.setAttribute(PageAttribute.MAP_PAGE_PARAMETERS, commandParams);
                    req.setAttribute(PageAttribute.IS_DUPLICATE_USER, "true");
                    page = PagePath.REGISTRY_PAGE;
                    return new Router(page, Router.Type.FORWARD);
                }
            } else {
                req.setAttribute(PageAttribute.MAP_PAGE_PARAMETERS, commandParams);
                page = PagePath.REGISTRY_PAGE;
                return new Router(page, Router.Type.FORWARD);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}

