package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * The type "Events" page command.
 * This command prepares data for "Events" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AdminEventsPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AdminEventsPageCommand.class);
    private static final EventService eventService = new EventServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Optional<String> numberPageOptional = Optional.empty();
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(PageCookieName.PAGINATION_NUMBER_PAGE)) {
                    numberPageOptional = Optional.of(cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                    break;
                }
            }
        }
        try {
            int currentPaginationPage = (numberPageOptional.isEmpty()) ?
                    Integer.parseInt(Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE)).
                            orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER))) :
                    Integer.parseInt(numberPageOptional.get());

            List<Event> listEvent = eventService.findAll(new Page(currentPaginationPage, ApplicationParam.DEFAULT_COUNT_EVENT_FOR_VIEW));
            int countOfEvents = eventService.count();
            int countPages = (int) Math.ceil(countOfEvents * 1.0 / Page.RECORD_NUMBER);
            req.setAttribute(PageAttribute.LIST_EVENT, listEvent);
            req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, currentPaginationPage);
            req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
            HttpSession session = req.getSession();
            session.setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.ADMIN_EVENTS.toString());
            return new Router(PagePath.ADMIN_EVENTS, Router.Type.FORWARD);
        } catch (ServiceException e) {
            e.printStackTrace();
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
