package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.web.command.Command;
import com.charakhovich.club.web.command.PageAttribute;
import com.charakhovich.club.web.command.PagePath;
import com.charakhovich.club.web.command.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        User authUser = (User) req.getSession().getAttribute(PageAttribute.AUTH_USER);
        req.getSession().invalidate();
        logger.log(Level.INFO, "User " + authUser.toString() + " logout");
        return new Router(PagePath.REDIRECT_MAIN, Router.Type.REDIRECT);
    }
}


