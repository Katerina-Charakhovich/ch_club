package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
/**
 * The type Change photo command.
 * This command allows to change photo in private cabinet
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class ChangeUserPhotoCommand implements Command {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(ChangeUserPhotoCommand.class);
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(PageAttribute.AUTH_USER);
        try {
            InputStream photo = req.getPart(PageParam.PARAM_USER_PHOTO).getInputStream();
            userService.updatePhoto(user.getUserId(), photo);
           Optional<User> authUser = userService.findUserByLogin(user.getLogin());
            session.setAttribute(PageAttribute.AUTH_USER, authUser.get());
        } catch (IOException | ServletException | ServiceException ex) {
            logger.log(Level.ERROR, ex);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(PagePath.PRIVATE_CABINET, Router.Type.FORWARD);
    }
}

