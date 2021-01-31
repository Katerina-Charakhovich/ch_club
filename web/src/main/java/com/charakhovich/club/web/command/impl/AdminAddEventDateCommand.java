package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventDateService;
import com.charakhovich.club.model.service.impl.EventDateServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.validation.DataValidate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
/**
 * The type add event date command.
 * This command allows to add new date
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AdminAddEventDateCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AdminAddEventDateCommand.class);
    private static final EventDateService eventDateService = new EventDateServiceImpl();
    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd'T'HH:mm";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        long eventId = Long.parseLong(req.getParameter(PageParam.PARAM_EVENT_ID));
        String dateTime = req.getParameter(PageParam.PARAM_EVENT_DATE_TIME);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER,
                Locale.getDefault()));
        boolean isValidTime = DataValidate.isValidEventTime(localDateTime);
        boolean isValidDate = DataValidate.isValidEventDate(localDateTime);
        double costTicketDouble = Double.parseDouble(req.getParameter(PageParam.PARAM_COST_TICKET));
        BigDecimal costTicket = BigDecimal.valueOf(costTicketDouble);
        boolean isValidCostTicket = DataValidate.isValidCostTicket(costTicket);
        int countTickets = Integer.parseInt(req.getParameter(PageParam.PARAM_COUNT_TICKETS));
        boolean isValidCountTicket = DataValidate.isValidCountTicket(countTickets);
        if (isValidTime && isValidCostTicket && isValidCountTicket && isValidDate) {
            EventDate eventDate = new EventDate(eventId, localDateTime, costTicket, countTickets);
            boolean isCreate = false;
            try {
                isCreate = eventDateService.create(eventDate) > 0;
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
            }
            if (isCreate) {
                resp.addCookie(new Cookie(PageCookieName.EVENT_ID, String.valueOf(eventId)));
                return new Router(PagePath.REDIRECT_ADMIN_EVENT_EDIT, Router.Type.REDIRECT);
            } else {
                req.setAttribute(PageAttribute.IS_INVALID_DATA, "true");
                req.setAttribute(PageAttribute.IS_EXIST_DATE, "true");
                resp.addCookie(new Cookie(PageCookieName.EVENT_ID, String.valueOf(eventId)));
                logger.log(Level.INFO,"The schedule can't be add ,"+localDateTime.toString() + " is busy");
                return new Router(PagePath.REDIRECT_ADMIN_EVENT_EDIT, Router.Type.FORWARD);
            }
        } else {
            req.setAttribute(PageAttribute.IS_INVALID_DATA, "true");
       //    req.setAttribute(PageAttribute.FORWARD_COMMAND, PagePath.REDIRECT_ADMIN_EVENT_EDIT); -->
            resp.addCookie(new Cookie(PageCookieName.EVENT_ID, String.valueOf(eventId)));
            if (!isValidTime) {
                req.setAttribute(PageAttribute.IS_INVALID_TIME, "true");
                logger.log(Level.INFO,"The schedule can't be add ,"+localDateTime.toString() + " is invalid time");
            }
            if (!isValidDate) {
                req.setAttribute(PageAttribute.IS_INVALID_DATE, "true");
                logger.log(Level.INFO,"The schedule can't be add ,"+localDateTime.toString() + " is invalid date");
            }
            if (!isValidCostTicket) {
                req.setAttribute(PageAttribute.IS_INVALID_COST_TICKET, "true");
                logger.log(Level.INFO,"The schedule can't be add , cost "+costTicket + " is invalid cost");
            }
            if (!isValidCountTicket) {
                req.setAttribute(PageAttribute.IS_INVALID_COUNT_TICKET, "true");
                logger.log(Level.INFO,"The schedule can't be add , cost "+countTickets + " is invalid count");
            }
            return new Router(PagePath.REDIRECT_ADMIN_EVENT_EDIT, Router.Type.FORWARD);
        }
    }
}
