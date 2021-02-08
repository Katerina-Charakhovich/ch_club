package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.impl.MessageEventServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.util.CookieHandler;
import com.charakhovich.club.web.validation.DataValidate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * The type add comment command.
 * This command allows to add a comment to a registered user
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AddMessageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddMessageCommand.class);
    private static final MessageEventServiceImpl messageEventService = new MessageEventServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User authUser = (User) session.getAttribute(PageAttribute.AUTH_USER);
        long eventId = Long.parseLong(req.getParameter(PageParam.PARAM_EVENT_ID));
        int numberPage = Integer.parseInt(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE));
        String message = req.getParameter(PageParam.PARAM_EVENT_MESSAGE).trim();
        boolean isMessageValid = DataValidate.isValidMessage(message);
        try {
            if (isMessageValid) {
                MessageEvent messageEvent = new MessageEvent();
                Event event=new Event();
                event.setEventId(eventId);
                messageEvent.setEvent(event);
                messageEvent.setModifyDate(LocalDateTime.now());
                messageEvent.setText(message);
                messageEvent.setState(MessageEvent.State.NEW);
                messageEvent.setUser(authUser);
                messageEventService.create(messageEvent);
                resp.addCookie(CookieHandler.create(PageCookieName.EVENT_ID, String.valueOf(eventId)));
                resp.addCookie(CookieHandler.create(PageCookieName.PAGINATION_NUMBER_PAGE, String.valueOf(numberPage)));
                resp.addCookie(CookieHandler.create(PageCookieName.IS_MESSAGE_VALID, "true"));
                return new Router(PagePath.REDIRECT_EVENT_VIEW, Router.Type.REDIRECT);
            } else {
                req.setAttribute(PageAttribute.EVENT_VIEW_ID, String.valueOf(eventId));
                req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, String.valueOf(numberPage));
                req.setAttribute(PageAttribute.IS_MESSAGE_VALID, "false");
                StringBuilder page=new StringBuilder(PagePath.REDIRECT_EVENT_VIEW).
                        append(ApplicationParam.ONE_PARAMETER_SPLIT).append(PageParam.PARAM_EVENT_ID).
                        append(ApplicationParam.SIGN_EQUALS).append(eventId);
                return new Router(page.toString(), Router.Type.FORWARD);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
