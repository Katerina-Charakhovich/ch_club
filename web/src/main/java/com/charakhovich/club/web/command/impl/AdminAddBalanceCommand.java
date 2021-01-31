package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.impl.UserServiceImpl;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.validation.DataValidate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * The type add balance command.
 * This command allows to top up user account for registered user
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class AdminAddBalanceCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AdminAddBalanceCommand.class);
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        long userAddBalanceId = Long.parseLong(req.getParameter(PageParam.PARAM_USER_ID));
        double amount = Double.parseDouble(req.getParameter(PageParam.ADD_BALANCE_AMOUNT));
        boolean isAddBalanceAmountValid = DataValidate.isAddBalanceAmountValid(BigDecimal.valueOf(amount));
        try {
            if (isAddBalanceAmountValid) {
                userService.addBalance(userAddBalanceId, BigDecimal.valueOf(amount));
                resp.addCookie(new Cookie(PageCookieName.IS_USER_ADD_BALANCE, String.valueOf(userAddBalanceId)));
                resp.addCookie(new Cookie(PageCookieName.IS_AMOUNT_VALID, "true"));
                logger.log(Level.INFO,"The balance for "+userAddBalanceId+" is added for amount "+amount);
            } else {
                resp.addCookie(new Cookie(PageCookieName.IS_USER_ADD_BALANCE, String.valueOf(userAddBalanceId)));
                resp.addCookie(new Cookie(PageCookieName.IS_AMOUNT_VALID, "true"));
                logger.log(Level.INFO,"The balance for "+userAddBalanceId+" isn't added. Amount "+amount);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return new Router(PagePath.REDIRECT_ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(PagePath.REDIRECT_ADMIN_USERS, Router.Type.REDIRECT);
    }

}

