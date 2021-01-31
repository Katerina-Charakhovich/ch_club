package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.service.MessageEventService;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.model.service.impl.MessageEventServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * The type "Event view" page command.
 * This command prepares data for "Event view" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class EventViewPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EventViewPageCommand.class);
    private static final EventService eventService = new EventServiceImpl();
    private static final MessageEventService messageEventService = new MessageEventServiceImpl();


    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Optional<Long> eventIdOptional = Optional.empty();
        Optional<String> numberPageOptional = Optional.empty();
        try {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies
            ) {
                if (cookie.getName().equals(PageCookieName.EVENT_ID)) {
                    eventIdOptional = Optional.of(Long.parseLong(cookie.getValue()));
                    resp.addCookie(CookieHandler.erase(cookie));
                }
                if (cookie.getName().equals(PageCookieName.PAGINATION_NUMBER_PAGE)) {
                    numberPageOptional = Optional.of(cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
                if (cookie.getName().equals(PageCookieName.IS_MESSAGE_VALID)) {
                    req.setAttribute(PageAttribute.IS_MESSAGE_VALID, cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                }
            }
            long eventId = (eventIdOptional.isEmpty()) ?
                    Long.parseLong(req.getParameter(PageAttribute.EVENT_VIEW_ID)) :
                    eventIdOptional.get();
            int numberPage = (numberPageOptional.isEmpty()) ?
                    Integer.parseInt(Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE)).
                            orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER))) :
                    Integer.parseInt(numberPageOptional.get());
            Optional<Event> event = eventService.findEntityById(eventId);
            int countOfMessages = messageEventService.countOfMessages(eventId);
            int countPages = (int) Math.ceil(countOfMessages * 1.0 / Page.RECORD_NUMBER);
            List<MessageEvent> listMessageEvent = messageEventService.findMessagesEvent(eventId,
                    new Page(numberPage, ApplicationParam.DEFAULT_COUNT_MESSAGE_EVENT_VIEW));
            StringBuilder currentCommandString = new StringBuilder(CommandType.EVENT_VIEW.toString()).
                    append(ApplicationParam.ONE_PARAMETER_SPLIT).append(PageAttribute.EVENT_VIEW_ID).
                    append(ApplicationParam.SIGN_EQUALS).append(event.get().getEventId()).
                    append(ApplicationParam.MULTI_PARAMETER_SPLIT).append(PageAttribute.PAGINATION_NUMBER_PAGE).
                    append(ApplicationParam.SIGN_EQUALS).append(numberPage).
                    append(ApplicationParam.MULTI_PARAMETER_SPLIT).append(PageAttribute.PAGINATION_COUNT_PAGES).
                    append(ApplicationParam.SIGN_EQUALS).append(countPages);
            req.setAttribute(PageAttribute.EVENT_VIEW, event.get());
            req.setAttribute(PageAttribute.EVENT_VIEW_ADDITIONAL_PICTURES, event.get().getListAdditionalPicture());
            req.setAttribute(PageAttribute.EVENT_MESSAGES, listMessageEvent);
            req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, numberPage);
            req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
            req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, currentCommandString.toString());
            return new Router(PagePath.EVENT_VIEW, Router.Type.FORWARD);
        } catch (ServiceException e) {
            e.printStackTrace();
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        } finally {
        }
    }
}
