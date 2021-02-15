package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type pagination page command.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class PaginationCommand implements Command {
    public static final String ONE_PARAMETER_SPLIT = "?";
    public static final String MULTI_PARAMETER_SPLIT = "&";
    public static final String CONTROLLER_NAME = "/do/";
    public static final String SIGN_EQUALS = "=";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String currentCommand = (String) session.getAttribute(PageAttribute.CURRENT_COMMAND);
        int number_page = Integer.parseInt(req.getParameter(PageParam.PARAM_PAGE));
        Cookie cookie = CookieHandler.create(PageCookieName.PAGINATION_NUMBER_PAGE, String.valueOf(number_page));
        resp.addCookie(cookie);
        StringBuilder page=currentCommand.contains(ONE_PARAMETER_SPLIT)? new StringBuilder().
                append(ApplicationParam.CONTROLLER_REGEX).append(currentCommand).
                append(MULTI_PARAMETER_SPLIT).append(PageAttribute.PAGINATION_NUMBER_PAGE).
                append(SIGN_EQUALS).append(number_page):
        new StringBuilder().append(ApplicationParam.CONTROLLER_REGEX).append(currentCommand)
                .append(ONE_PARAMETER_SPLIT).append(PageAttribute.PAGINATION_NUMBER_PAGE).append(SIGN_EQUALS).append(number_page);
        return new Router(page.toString(), Router.Type.REDIRECT);
    }
}

