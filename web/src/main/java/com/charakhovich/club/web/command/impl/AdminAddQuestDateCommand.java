package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventDateService;
import com.charakhovich.club.model.service.impl.EventDateServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import com.charakhovich.club.web.validation.DataValidate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The type empty command.
 * This command allows to add new date for quest.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AdminAddQuestDateCommand implements Command {
    private static final String DATE_FORMATTER = "yyyy-MM-dd";
    private static final String TIME_FORMATTER = "HH:mm";
    public static final String ONE_PARAMETER_SPLIT = "?";
    public static final String MULTI_PARAMETER_SPLIT = "&";
    public static final String CONTROLLER_REGEX = "/do/";
    public static final String SIGN_EQUALS = "=";
    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd'T'HH:mm";
    private static final Logger logger = LogManager.getLogger(AdminAddEventDateCommand.class);
    private static final EventDateService eventDateService = new EventDateServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {

        long eventId = Long.parseLong(req.getParameter(PageParam.PARAM_EVENT_ID));
        String date = req.getParameter(PageParam.PARAM_QUEST_DATE);
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMATTER, Locale.getDefault()));
        boolean isValidDate = DataValidate.isValidEventDate(localDate);
        String startTime = req.getParameter(PageParam.PARAM_QUEST_START_TIME);
        String endTime = req.getParameter(PageParam.PARAM_QUEST_END_TIME);
        LocalTime localStartTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern(TIME_FORMATTER, Locale.getDefault()));
        LocalTime localEndTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern(TIME_FORMATTER, Locale.getDefault()));
        boolean isValidStartTime = DataValidate.isValidEventTime(localStartTime);
        boolean isValidEndTime = DataValidate.isValidEventTime(localEndTime);
        double costTicketDouble = Double.parseDouble(req.getParameter(PageParam.PARAM_QUEST_COST_TICKET));
        BigDecimal costTicket = BigDecimal.valueOf(costTicketDouble);
        boolean isValidCostTicket = DataValidate.isValidCostTicket(costTicket);

        if (isValidDate && isValidCostTicket && isValidStartTime && isValidEndTime) {
            boolean isCreate = false;
            try {
                isCreate = eventDateService.createQuestSchedule(eventId, localDate, localStartTime, localEndTime,costTicket) > 0 ?
                        true : false;
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
            }
            if (isCreate) {
                resp.addCookie(CookieHandler.create(PageCookieName.EVENT_ID, String.valueOf(eventId)));
                return new Router(PagePath.REDIRECT_ADMIN_EVENT_EDIT, Router.Type.REDIRECT);
            } else {
                req.setAttribute(PageAttribute.IS_INVALID_QUEST_DATA, "true");
                req.setAttribute(PageAttribute.IS_EXIST_QUEST_DATE, "true");
                logger.log(Level.INFO, "The schedule can't be add ," + localDate.toString() + " is busy");
                StringBuilder stringBuilder=new StringBuilder(PagePath.REDIRECT_ADMIN_EVENT_EDIT).
                        append(ApplicationParam.ONE_PARAMETER_SPLIT).append(PageParam.PARAM_EVENT_ID).
                        append(ApplicationParam.SIGN_EQUALS).append(eventId);
                return new Router(stringBuilder.toString(), Router.Type.FORWARD);
            }
        } else {
            req.setAttribute(PageAttribute.IS_INVALID_QUEST_DATA, "true");
            if (!(isValidStartTime && isValidEndTime)) {
                req.setAttribute(PageAttribute.IS_INVALID_QUEST_TIME, "true");
                logger.log(Level.INFO, "The schedule can't be add  is invalid time");
            }
            if (!isValidDate) {
                req.setAttribute(PageAttribute.IS_INVALID_QUEST_DATE, "true");
                logger.log(Level.INFO, "The schedule can't be add ," + localDate.toString() + " is invalid date");
            }
            if (!isValidCostTicket) {
                req.setAttribute(PageAttribute.IS_INVALID_QUEST_COST_TICKET, "true");
                logger.log(Level.INFO, "The schedule can't be add , cost " + costTicket + " is invalid cost");
            }
            StringBuilder stringBuilder=new StringBuilder(PagePath.REDIRECT_ADMIN_EVENT_EDIT).
                    append(ApplicationParam.ONE_PARAMETER_SPLIT).append(PageParam.PARAM_EVENT_ID).
                    append(ApplicationParam.SIGN_EQUALS).append(eventId);
            return new Router(stringBuilder.toString(), Router.Type.FORWARD);
        }
    }
}
