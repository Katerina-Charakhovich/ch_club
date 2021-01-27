package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Picture;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.web.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EventAddCommand implements Command {
    private static EventService eventService = new EventServiceImpl();
    static final Logger logger = LogManager.getLogger(EventAddCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String eventName = req.getParameter(PageParam.PARAM_EVENT_ADD_NAME);
            Event.Type eventType = Event.Type.valueOf(req.getParameter(PageAttribute.EVENT_TYPE).toUpperCase());
            String eventDescription = req.getParameter(PageParam.EVENT_DESCRIPTION);
            String eventShortDescription = req.getParameter(PageParam.EVENT_SHORT_DESCRIPTION);
            double eventDuration = Double.parseDouble(req.getParameter(PageParam.PARAM_EVENT_DURATION));
            Event event = new Event(eventType, eventName, eventDescription, eventShortDescription,eventDuration);
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
            boolean isCreate = eventService.create(event, mainPicture, addListPicture);
            return isCreate ? new
                    Router(PagePath.ADMIN_EVENT_VIEW, Router.Type.FORWARD) : new
                    Router(PagePath.ADMIN_EVENT_VIEW, Router.Type.FORWARD);
        } catch (IOException | ServletException | ServiceException ex) {
            logger.log(Level.ERROR, ex);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}

