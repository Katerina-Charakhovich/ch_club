package com.charakhovich.club.web.command.impl.navigation;

import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.MessageEventService;
import com.charakhovich.club.model.service.impl.MessageEventServiceImpl;
import com.charakhovich.club.web.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * The type "Messages" page command.
 * This command prepares data for "Messages" page view
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AdminMessagePageCommand implements Command {
    private static final MessageEventService messageEventService = new MessageEventServiceImpl();
    private static final Logger logger = LogManager.getLogger(AdminMessagePageCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int currentPaginationPage = Integer.parseInt(Optional.ofNullable(req.getParameter(PageAttribute.PAGINATION_NUMBER_PAGE))
                    .orElse(String.valueOf(ApplicationParam.DEFAULT_PAGINATION_NUMBER)));
            List<MessageEvent> listMessageEvent = messageEventService.findMessagesEvent(MessageEvent.State.NEW,
                    new Page(currentPaginationPage, ApplicationParam.DEFAULT_COUNT_MESSAGE_EVENT_VIEW));
            int countOfMessage = messageEventService.countOfMessageByState(MessageEvent.State.NEW);
            int countPages = (int) Math.ceil(countOfMessage * 1.0 / Page.RECORD_NUMBER);
            req.setAttribute(PageAttribute.EVENT_MESSAGES, listMessageEvent);
            req.setAttribute(PageAttribute.PAGINATION_NUMBER_PAGE, currentPaginationPage);
            req.setAttribute(PageAttribute.PAGINATION_COUNT_PAGES, countPages);
            HttpSession session = req.getSession();
            session.setAttribute(PageAttribute.CURRENT_COMMAND, CommandType.ADMIN_MESSAGES.toString());
            return new Router(PagePath.ADMIN_MESSAGES, Router.Type.FORWARD);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
