package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.MessageEventService;
import com.charakhovich.club.model.service.impl.MessageEventServiceImpl;
import com.charakhovich.club.web.command.Command;
import com.charakhovich.club.web.command.PageParam;
import com.charakhovich.club.web.command.PagePath;
import com.charakhovich.club.web.command.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type empty command.
 * This command allows to cancel user comment
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AdminMessageCancelCommand implements Command {
    private static final MessageEventService messageEventService = new MessageEventServiceImpl();
    private static final Logger logger = LogManager.getLogger(AdminMessageCancelCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            long messageId = Long.parseLong(req.getParameter(PageParam.PARAM_MESSAGE_ID));
            messageEventService.updateState(messageId, MessageEvent.State.CANCEL);
            logger.log(Level.INFO, "Message " + messageId + " cancelled");
            return new Router(PagePath.REDIRECT_ADMIN_MESSAGES, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
    }
}
