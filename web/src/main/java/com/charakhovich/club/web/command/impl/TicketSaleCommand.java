package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.*;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventDateService;
import com.charakhovich.club.model.service.impl.EventDateServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


public class TicketSaleCommand implements Command {
    private static final Logger logger = LogManager.getLogger(TicketSaleCommand.class);
    private static final EventDateService eventDateService = new EventDateServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        RequestContext requestContext = new RequestContext(req);
        HashMap<String, String> commandParams = requestContext.getReqParams();

        try {
            User user = (User) req.getSession().getAttribute(PageAttribute.AUTH_USER);
            long eventId = commandParams.containsKey(PageParam.PARAM_EVENT_ID)
                    ? Long.parseLong(req.getParameter(PageParam.PARAM_EVENT_ID))
                    : -1;
            long eventDateId = commandParams.containsKey(PageParam.PARAM_SCHEDULE_ID)
                    ? Long.parseLong(req.getParameter(PageParam.PARAM_SCHEDULE_ID))
                    : -1;
            int countTicket = commandParams.containsKey(PageParam.PARAM_COUNT_TICKETS)
                    ? Integer.parseInt(req.getParameter(PageParam.PARAM_COUNT_TICKETS))
                    : ApplicationParam.DEFAULT_COUNT_TICKET;
            Event.Type eventType = commandParams.containsKey(PageParam.PARAM_EVENT_TYPE)
                    ? Event.Type.valueOf(req.getParameter(PageParam.PARAM_EVENT_TYPE).toUpperCase())
                    : null;
            Ticket.State ticketState = commandParams.containsKey(PageParam.PARAM_TICKET_STATE)
                    ? Ticket.State.valueOf(req.getParameter(PageParam.PARAM_TICKET_STATE).toUpperCase())
                    : Ticket.State.BOOKED;
            Ticket ticket = new Ticket(eventDateId, user.getUserId(),ticketState,countTicket );
            if (eventDateService.registryTicket(ticket)>0){
                resp.addCookie(CookieHandler.create(PageCookieName.IS_TICKET_SALE, "true"));
                resp.addCookie(CookieHandler.create(PageCookieName.EVENT_TICKET_SALE_ID, String.valueOf(eventId)));
                return new Router(PagePath.REDIRECT_EVENT_TICKET_SALE, Router.Type.REDIRECT);
            }else {
                resp.addCookie(CookieHandler.create(PageCookieName.IS_TICKET_SALE,"false" ));
                resp.addCookie(CookieHandler.create(PageCookieName.EVENT_TICKET_SALE_ID, String.valueOf(eventId)));
                return new Router(PagePath.REDIRECT_EVENT_TICKET_SALE, Router.Type.REDIRECT);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            e.printStackTrace();
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
