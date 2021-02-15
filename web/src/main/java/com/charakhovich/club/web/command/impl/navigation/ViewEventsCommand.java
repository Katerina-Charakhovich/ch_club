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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The type "View events" page command.
 * This command prepares data for "View events" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class ViewEventsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewEventsCommand.class);
    private static final EventService eventService = new EventServiceImpl();
    public static final String ONE_PARAMETER_SPLIT = "?";
    public static final String SIGN_EQUALS = "=";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Optional<Integer> numberPageOptional = Optional.empty();
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies
        ) {
            if (cookie.getName().equals(PageCookieName.PAGINATION_NUMBER_PAGE)) {
                numberPageOptional = Optional.of(Integer.parseInt(cookie.getValue()));
                resp.addCookie(CookieHandler.erase(cookie));
                break;
            }
        }
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();
        int numberPage = numberPageOptional.isEmpty() ?
                commandParams.containsKey(PageAttribute.PAGINATION_NUMBER_PAGE) ?
                        Integer.parseInt(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE)) :
                        ApplicationParam.DEFAULT_PAGINATION_NUMBER :
                numberPageOptional.get();
        String typeEventString = commandParams.containsKey(PageParam.PARAM_EVENT_TYPE)
                ? req.getParameter(PageParam.PARAM_EVENT_TYPE).toUpperCase()
                : null;
        long flag = Arrays.stream(Event.Type.values()).map(s -> s.toString()).filter(s -> s.equals(typeEventString)).count();
        Event.Type typeEvent = flag > 0 ? Event.Type.valueOf(typeEventString) : null;
        List<Event> listEvent = null;
        StringBuilder currentCommandString = new StringBuilder(CommandType.EVENTS.toString());
        int countOfEvents = 0;
        int countPages=0;
        String page = PagePath.EVENTS;
        try {
            switch (typeEvent) {
                case THEATRE:
                    countOfEvents = eventService.count(typeEvent);
                    countPages = (int) Math.ceil(countOfEvents * 1.0 / Page.RECORD_NUMBER);
                    numberPage=numberPage>countPages&&countPages!=0?countPages:numberPage<1?ApplicationParam.DEFAULT_PAGINATION_NUMBER:numberPage;
                    listEvent = eventService.findAll(Event.Type.THEATRE,
                            new Page(numberPage, ApplicationParam.DEFAULT_COUNT_EVENT_FOR_VIEW));
                    currentCommandString.append(ONE_PARAMETER_SPLIT).append(PageParam.PARAM_EVENT_TYPE).append(SIGN_EQUALS).append(Event.Type.THEATRE.toString());

                    break;
                case QUEST:
                    countOfEvents = eventService.count(typeEvent);
                    countPages = (int) Math.ceil(countOfEvents * 1.0 / Page.RECORD_NUMBER);
                    numberPage=numberPage>countPages?countPages:numberPage<1?ApplicationParam.DEFAULT_PAGINATION_NUMBER:numberPage;
                    listEvent = eventService.findAll(Event.Type.QUEST, new Page(numberPage, ApplicationParam.DEFAULT_COUNT_EVENT_FOR_VIEW));
                    currentCommandString.append(ONE_PARAMETER_SPLIT).append(PageParam.PARAM_EVENT_TYPE).append(SIGN_EQUALS).append(Event.Type.QUEST.toString());

                    break;
                default:
                    countOfEvents = eventService.count();
                    countPages = (int) Math.ceil(countOfEvents * 1.0 / Page.RECORD_NUMBER);
                    numberPage=numberPage>countPages?countPages:numberPage<1?ApplicationParam.DEFAULT_PAGINATION_NUMBER:numberPage;
                    listEvent = eventService.findAll(new Page(numberPage, ApplicationParam.DEFAULT_COUNT_EVENT_FOR_VIEW));


            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        req.setAttribute(PageAttribute.LIST_EVENT, listEvent);
        req.setAttribute(PageAttribute.EVENT_TYPE, typeEvent);
        req.getSession().setAttribute(PageAttribute.CURRENT_COMMAND, currentCommandString.toString());
        req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, numberPage);
        req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
        return new Router(page, Router.Type.FORWARD);
    }
}
