package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventDateService;
import com.charakhovich.club.model.service.impl.EventServiceImpl;
import com.charakhovich.club.model.service.impl.EventDateServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AdminEventEditPageCommand implements Command {
    private static final EventServiceImpl eventService = new EventServiceImpl();
    private static final EventDateService eventDateService = new EventDateServiceImpl();
    public static final String ONE_PARAMETER_SPLIT = "?";
    public static final String MULTI_PARAMETER_SPLIT = "&";
    public static final String CONTROLLER_NAME = "/do/";
    public static final String SIGN_EQUALS = "=";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        long eventId = -1;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (PageCookieName.EVENT_ID.equals(cookie.getName())) {
                    req.setAttribute(PageAttribute.EVENT_VIEW_ID, cookie.getValue());
                    eventId = Long.parseLong(cookie.getValue());
                    resp.addCookie(CookieHandler.erase(cookie));
                    break;
                }
            }
        }
        try {
            eventId = (0 > eventId) ? Long.parseLong(req.getParameter(PageParam.PARAM_EVENT_ID)) : eventId;
            Optional<Event> event = eventService.findEntityById(eventId);
            if (event.get().getEventType() == Event.Type.THEATRE) {
                int countDates = eventDateService.count(eventId);
                int countPages = (int) Math.ceil(countDates * 1.0 / Page.RECORD_NUMBER);
                String str = Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE)).
                        orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER));
                int numberPage = Integer.parseInt(str);
                List<EventDate> listEventDates = eventDateService.findEventDates(eventId,
                        new Page(numberPage, ApplicationParam.DEFAULT_COUNT_MESSAGE_EVENT_VIEW));
                req.setAttribute(PageAttribute.EVENT_VIEW, event.get());
                req.setAttribute(PageAttribute.EVENT_DATES, listEventDates);
                req.setAttribute(PageAttribute.EVENT_VIEW_ADDITIONAL_PICTURES, event.get().getListAdditionalPicture());
                req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, numberPage);
                req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
                StringBuilder stringBuilder = new StringBuilder(CommandType.ADMIN_EVENT_EDIT.toString()).
                        append(ONE_PARAMETER_SPLIT).append(PageParam.PARAM_EVENT_ID).append(SIGN_EQUALS).append(String.valueOf(eventId));
                req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, stringBuilder.toString());
            }
            if (event.get().getEventType() == Event.Type.QUEST) {
                int countDates = eventDateService.countDates(eventId);
                int countPages = (int) Math.ceil(countDates * 1.0 / Page.RECORD_NUMBER);
                String str = Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE)).orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER));
                int numberPage = Integer.parseInt(str);
                HashMap<String, List<EventDate>> dates = new HashMap<>();
                List<LocalDate> listLocalDates;
                listLocalDates = eventDateService.findEventDatesQuest(eventId,
                        new Page(numberPage, ApplicationParam.DEFAULT_COUNT_VIEW_QUEST_DATES));
                for (LocalDate localDate : listLocalDates
                ) {
                    List<EventDate> listEventDates = eventDateService.findEventDates(eventId, localDate);
                    String strDate = localDate.getDayOfMonth() + " " + localDate.getMonth() + " " + localDate.getDayOfWeek();
                    dates.put(strDate, listEventDates);
                }
                req.setAttribute(PageAttribute.EVENT_VIEW, event.get());
                req.setAttribute("questdates", dates);
                req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, numberPage);
                req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
                req.setAttribute(PageAttribute.EVENT_VIEW_ADDITIONAL_PICTURES, event.get().getListAdditionalPicture());
                req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.ADMIN_EVENT_EDIT.toString());
            }

        } catch (ServiceException e) {
            e.printStackTrace();
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(PagePath.ADMIN_EVENT_EDIT, Router.Type.FORWARD);
    }
}
