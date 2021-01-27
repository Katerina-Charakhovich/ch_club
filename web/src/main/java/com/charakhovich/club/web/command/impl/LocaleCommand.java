package com.charakhovich.club.web.command.impl;
import com.charakhovich.club.web.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class LocaleCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp)  {
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        HttpSession session = req.getSession();
        String locale = String.valueOf(req.getParameter(PageAttribute.LOCALE));
        session.setAttribute(PageAttribute.LOCALE, locale);
        String currentCommand = (String) session.getAttribute(PageAttribute.CURRENT_COMMAND);
        if (currentCommand==null) {
            currentCommand= CommandType.MAIN_PAGE_VIEW.name();
        }
        StringBuilder page = new StringBuilder().append(ApplicationParam.CONTROLLER_REGEX).append(currentCommand);
        return new Router(page.toString(), Router.Type.REDIRECT);
    }
}
