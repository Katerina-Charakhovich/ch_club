package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.*;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventDateService;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.service.impl.EventDateServiceImpl;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type "Ticket sale" page command.
 * This command prepares data for "Ticket sale" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class EventTicketSalePageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EventTicketSalePageCommand.class);
    private static final EventService eventService = new EventServiceImpl();
    private static final EventDateService eventDateService = new EventDateServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            long eventId = -1;
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(PageCookieName.EVENT_ID)) {
                        eventId = Long.parseLong(cookie.getValue());
                        resp.addCookie(CookieHandler.erase(cookie));
                    }
                    if (cookie.getName().equals(PageCookieName.IS_TICKET_BOOKED)) {
                        req.setAttribute(PageAttribute.IS_TICKET_BOOKED, cookie.getValue());
                        resp.addCookie(CookieHandler.erase(cookie));
                    }
                    if (cookie.getName().equals(PageCookieName.IS_TICKET_PAID)) {
                        req.setAttribute(PageAttribute.IS_TICKET_PAID, cookie.getValue());
                        resp.addCookie(CookieHandler.erase(cookie));
                    }
                    if (cookie.getName().equals(PageCookieName.IS_TICKET_SALE)) {
                        req.setAttribute(PageAttribute.IS_TICKET_SALE, cookie.getValue());
                        resp.addCookie(CookieHandler.erase(cookie));
                    }
                }
            }
            eventId = eventId < 0 ? Long.parseLong(req.getParameter(PageParam.PARAM_EVENT_ID)) : eventId;
            Optional<Event> event = eventService.findEntityById(eventId);
            if (event.get().getEventType() == Event.Type.THEATRE) {
                int countDates = eventDateService.count(eventId);
                int countPages = (int) Math.ceil(countDates * 1.0 / Page.RECORD_NUMBER);
                String str = Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE)).
                        orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER));
                int numberPage = Integer.parseInt(str);
                          List<EventDate> listEventDates = eventDateService.findEventAvailableDates(eventId,
                //  List<EventDate> listEventDates = eventDateService.findEventDates(eventId,
                        new Page(numberPage, ApplicationParam.DEFAULT_COUNT_MESSAGE_EVENT_VIEW));
                req.setAttribute(PageAttribute.EVENT_VIEW, event.get());
                req.setAttribute(PageAttribute.EVENT_VIEW_ADDITIONAL_PICTURES, event.get().getListAdditionalPicture());
                req.setAttribute(PageAttribute.EVENT_DATES, listEventDates);
                req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, numberPage);
                req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
                req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.EVENT_TICKET_SALE.toString());
            }
            if (event.get().getEventType() == Event.Type.QUEST) {
                //         int countDates = eventDateService.countAvailableDates(eventId);
                int countDates = eventDateService.countDates(eventId);
                int countPages = (int) Math.ceil(countDates * 1.0 / Page.RECORD_NUMBER);
                String str = Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE)).
                        orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER));
                int numberPage = Integer.parseInt(str);
                HashMap<String, List<EventDate>> dates = new HashMap<>();
                List<LocalDate> listLocalDates;
                listLocalDates = eventDateService.findAvailableEventDatesQuest(eventId,
   //             listLocalDates = eventDateService.findEventDatesQuest(eventId,
                        new Page(numberPage, ApplicationParam.DEFAULT_COUNT_VIEW_QUEST_DATES));
                for (LocalDate localDate : listLocalDates
                ) {
                    List<EventDate> listEventDatesTemp = eventDateService.findEventDates(eventId, localDate);
                    List<EventDate> listEventDates = listEventDatesTemp.stream().filter(s -> s.getState() == EventDate.State.BOOKED ||
                            s.getState() == EventDate.State.ACTUAL).collect(Collectors.toList());
                    String strDate = localDate.getDayOfMonth() + " " + localDate.getMonth() + " " + localDate.getDayOfWeek();
                    dates.put(strDate, listEventDates);
                }
                req.setAttribute(PageAttribute.EVENT_VIEW, event.get());
                req.setAttribute(PageAttribute.EVENT_VIEW_ADDITIONAL_PICTURES, event.get().getListAdditionalPicture());
                req.setAttribute(PageAttribute.QUEST_SCHEDULE, dates);
                req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, numberPage);
                req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
                req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.EVENT_TICKET_SALE.toString());
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            e.printStackTrace();
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(PagePath.EVENT_TICKET_SALE, Router.Type.FORWARD);
    }
}