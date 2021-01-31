package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type "Registry" page command.
 * This command prepares data for "Registry" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class RegistrySuccessPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (PageCookieName.PRE_REGISTRY.equals(cookie.getName())) {
                    req.setAttribute(PageAttribute.PRE_REGISTRY, cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
                if (PageCookieName.IS_SUCCESS_REGISTRY.equals(cookie.getName())) {
                    req.setAttribute(PageAttribute.IS_SUCCESS_REGISTRY, cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
            }
        }
        return new Router(PagePath.SUCCESS_PAGE, Router.Type.FORWARD);
    }
}
