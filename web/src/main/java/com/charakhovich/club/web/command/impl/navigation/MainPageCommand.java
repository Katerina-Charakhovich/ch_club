package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.web.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MainPageCommand.class);
    private static final EventService eventService = new EventServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        String page;
        try {
            List<Event> listTheatre = eventService.findAll(Event.Type.THEATRE,
                    new Page(ApplicationParam.DEFAULT_PAGINATION_NUMBER, 3));
            List<Event> listQuest = eventService.findAll(Event.Type.QUEST, 
                    new Page(ApplicationParam.DEFAULT_PAGINATION_NUMBER, 3));
            req.setAttribute(PageAttribute.LIST_THEATRE, listTheatre);
            req.setAttribute(PageAttribute.LIST_QUEST, listQuest);
            req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.MAIN_PAGE_VIEW.toString());
            page = PagePath.MAIN;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(page, Router.Type.FORWARD);
    }
}
