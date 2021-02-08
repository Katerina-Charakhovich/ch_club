package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.charakhovich.club.web.validation.DataValidate.*;

/**
 * The type update event info command.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class UpdateEventInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private static EventService eventService = new EventServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        long eventId = commandParams.containsKey(PageParam.PARAM_EVENT_UPDATE_ID)
                ? Long.parseLong(req.getParameter(PageParam.PARAM_EVENT_UPDATE_ID))
                : -1;;
        boolean isValidEventUpdate = isValidEventUpdate(commandParams);
        try {
            if (isValidEventUpdate) {

                Event event = eventService.findEntityById(eventId).get();
                if (commandParams.containsKey(PageParam.EVENT_DESCRIPTION)) {
                    event.setDescription(commandParams.get(PageParam.EVENT_DESCRIPTION));
                }
                if (commandParams.containsKey(PageParam.EVENT_SHORT_DESCRIPTION)) {
                    event.setShortDescription(commandParams.get(PageParam.EVENT_SHORT_DESCRIPTION));
                }
                if (commandParams.containsKey(PageParam.PARAM_EVENT_NAME)) {
                    event.setName(commandParams.get(PageParam.PARAM_EVENT_NAME));
                }
                boolean isUpdateEvent = eventService.update(event);
                if (isUpdateEvent) {
                    resp.addCookie(CookieHandler.create(PageCookieName.IS_UPDATE_EVENT, "true"));
                    resp.addCookie(CookieHandler.create(PageCookieName.EVENT_ID, String.valueOf(eventId)));
                    return new Router(PagePath.REDIRECT_ADMIN_EVENT_EDIT, Router.Type.REDIRECT);
                } else
                    req.setAttribute(PageAttribute.IS_UPDATE_EVENT, "false");
            } else {
                req.setAttribute(PageAttribute.MAP_PAGE_PARAMETERS, commandParams);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        StringBuilder page=new StringBuilder(PagePath.REDIRECT_ADMIN_EVENT_EDIT).
                append(ApplicationParam.ONE_PARAMETER_SPLIT).append(PageParam.PARAM_EVENT_ID).
                append(ApplicationParam.SIGN_EQUALS).append(eventId);
        return new Router(page.toString(), Router.Type.FORWARD);
    }
}

