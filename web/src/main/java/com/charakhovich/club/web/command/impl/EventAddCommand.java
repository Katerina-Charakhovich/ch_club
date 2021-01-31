package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Picture;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.validation.DataValidate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * The type add event command.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class EventAddCommand implements Command {
    private static EventService eventService = new EventServiceImpl();
    static final Logger logger = LogManager.getLogger(EventAddCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RequestContext requestContext = new RequestContext(req);
            HashMap<String, String> commandParams = requestContext.getReqParams();
            boolean isValidNewEvent = DataValidate.isValidEventNew(commandParams);
            if (isValidNewEvent) {
                String eventName = commandParams.get(PageParam.PARAM_EVENT_ADD_NAME);
                Event.Type eventType = Event.Type.valueOf(commandParams.get(PageAttribute.EVENT_TYPE).toUpperCase());
                String eventDescription = commandParams.get(PageParam.EVENT_DESCRIPTION);
                String eventShortDescription = commandParams.get(PageParam.EVENT_SHORT_DESCRIPTION);
                double eventDuration = Double.parseDouble(commandParams.get(PageParam.PARAM_EVENT_DURATION));
                Event event = new Event(eventType, eventName, eventDescription, eventShortDescription, eventDuration);
                List<Part> list = (List<Part>) req.getParts();
                List<InputStream> addListPicture = new ArrayList<>();
                InputStream mainPicture = null;
                for (Part item : list) {
                    if (item.getSubmittedFileName() != null) {
                        Picture.Type typePicture = Picture.Type.valueOf(item.getName().toUpperCase());
                        if (typePicture == Picture.Type.MAIN) {
                            mainPicture = item.getInputStream();
                        }
                        if (typePicture == Picture.Type.ADDITIONAL) {
                            addListPicture.add(item.getInputStream());
                        }
                    }
                }
                long resultEventId = eventService.create(event, mainPicture, addListPicture);
                if (resultEventId > 0) {
                    resp.addCookie(new Cookie(PageCookieName.EVENT_ID, String.valueOf(resultEventId)));
                    return new Router(PagePath.REDIRECT_ADMIN_EVENT_VIEW, Router.Type.REDIRECT);
                } else {
                    return new Router(PagePath.ADMIN_EVENTS, Router.Type.REDIRECT);
                }
            } else {
                req.setAttribute(PageAttribute.MAP_PAGE_PARAMETERS, commandParams);
                return new Router(PagePath.EVENT_ADD, Router.Type.FORWARD);
            }
        } catch (IOException | ServletException | ServiceException ex) {
            logger.log(Level.ERROR, ex);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}

