package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrivateCabinetPageCommand implements Command {
  private static final Logger logger = LogManager.getLogger(PrivateCabinetPageCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (PageCookieName.IS_CORRECT_CHANGE_PASSWORD.equals(cookie.getName())) {
                    req.setAttribute(PageAttribute.IS_CORRECT_CHANGE_PASSWORD,cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
                if (PageCookieName.IS_UPDATE_USER.equals(cookie.getName())) {
                    req.setAttribute(PageAttribute.IS_UPDATE_USER,cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
            }
        }
        req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.PRIVATE_CABINET.toString());
        return new Router(PagePath.PRIVATE_CABINET, Router.Type.FORWARD);
    }
}

