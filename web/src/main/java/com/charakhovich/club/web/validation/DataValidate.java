package com.charakhovich.club.web.validation;

import com.charakhovich.club.web.command.PageParam;
import com.charakhovich.club.web.command.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import static com.charakhovich.club.web.command.PageAttribute.*;

public class DataValidate {
    static final Logger logger = LogManager.getLogger(DataValidate.class);
    public static final String ERROR_MESSAGE = "error";
    public static final String XSS_ATTACK = "</?script>";
    //private static final String PASSWORD_PATTERN = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^(.{8,15})$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w]{2,4}$";
    private static final String FIRSTNAME_PATTERN = "[а-яА-Яa-zA-Z]{2,20}";
    private static final String LASTNAME_PATTERN = "[а-яА-Яa-zA-Z]{2,45}";
    private static final String PHONE_PATTERN = "^[\\d]{12}$";
    private static final LocalTime MIN_LOCAL_TIME = LocalTime.of(9, 00);
    private static final LocalTime MAX_LOCAL_TIME = LocalTime.of(20, 00);
    private static final BigDecimal MIN_COST = BigDecimal.valueOf(5);
    private static final BigDecimal MAX_COST = BigDecimal.valueOf(50);
    private static final int MIN_COUNT = 1;
    private static final int MAX_COUNT = 30;
    private static final BigDecimal MIN_ADD_BALANCE_AMOUNT = BigDecimal.valueOf(0.5);
    private static final BigDecimal MAX_ADD_BALANCE_AMOUNT = BigDecimal.valueOf(100.00);

    public static boolean isValidPageParameters(RequestContext requestContext) {
        boolean result = true;
        for (Map.Entry<String, String> entry : requestContext.getReqParams().entrySet()) {
            switch (entry.getKey()) {
                case USER_LOGIN:
                    String value = (String) entry.getValue();
                    boolean flag = value.matches(EMAIL_PATTERN);
                    if (!flag) {
                        entry.setValue(ERROR_MESSAGE);
                        result = false;
                    }
                    break;
                case USER_PASSWORD:
                    value = (String) entry.getValue();
                    flag = value.matches(PASSWORD_PATTERN);
                    if (!flag) {
                        entry.setValue(ERROR_MESSAGE);
                        result = false;
                    }
                    break;
                case EVENT_DESCRIPTION:
                    value = (String) entry.getValue();
                    flag = value.matches(XSS_ATTACK);
                    if (flag) {
                        entry.setValue(ERROR_MESSAGE);
                        result = false;
                    }
                default:
                    value = (String) entry.getValue();
                    flag = value.matches(XSS_ATTACK);
                    if (flag) {
                        entry.setValue(ERROR_MESSAGE);
                        result = false;
                    }
            }
        }
        return result;
    }

    public static boolean isValidEventDate(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate.compareTo(LocalDate.now().plusDays(1)) > -1 ? true : false;
    }
    public static boolean isValidEventDate(LocalDate localDate) {
        return localDate.compareTo(LocalDate.now().plusDays(1)) > -1 ? true : false;
    }
    public static boolean isValidCostTicket(BigDecimal cost) {
        return (cost.compareTo(MIN_COST)>-1)&&(cost.compareTo(MAX_COST)<0);
    }
    public static boolean isValidCountTicket(int  count) {
        return (count>=MIN_COUNT)&&(count<=MAX_COUNT);
    }

    public static boolean isValidEventTime(LocalDateTime localDateTime) {
        LocalTime localTime = localDateTime.toLocalTime();
        return (localTime.compareTo(MIN_LOCAL_TIME)>-1)&&(localTime.compareTo(MAX_LOCAL_TIME)<0);
    }
    public static boolean isValidEventTime(LocalTime localTime) {
        return (localTime.compareTo(MIN_LOCAL_TIME)>-1)&&(localTime.compareTo(MAX_LOCAL_TIME)<0);
    }
    public static boolean isAddBalanceAmountValid(BigDecimal amount) {
        return (amount.compareTo(MIN_ADD_BALANCE_AMOUNT)>-1)&&(amount.compareTo(MAX_ADD_BALANCE_AMOUNT)<0);
    }

    public static boolean isValidUserNew(Map<String, String> params) {

        params.put(PageParam.PARAM_USER_LOGIN, params.containsKey(PageParam.PARAM_USER_LOGIN) ?
                params.get(PageParam.PARAM_USER_LOGIN).matches(EMAIL_PATTERN) ? params.get(PageParam.PARAM_USER_LOGIN) :
                        ERROR_MESSAGE : ERROR_MESSAGE);
        params.put(PageParam.PARAM_USER_LASTNAME, params.containsKey(PageParam.PARAM_USER_LASTNAME) ?
                params.get(PageParam.PARAM_USER_LASTNAME).matches(LASTNAME_PATTERN) ? params.get(PageParam.PARAM_USER_LASTNAME)
                        : ERROR_MESSAGE : ERROR_MESSAGE);
        params.put(PageParam.PARAM_USER_FIRSTNAME, params.containsKey(PageParam.PARAM_USER_FIRSTNAME) ?
                params.get(PageParam.PARAM_USER_FIRSTNAME).matches(FIRSTNAME_PATTERN) ? params.get(PageParam.PARAM_USER_FIRSTNAME)
                        : ERROR_MESSAGE : ERROR_MESSAGE);
        params.put(PageParam.PARAM_USER_PASSWORD, params.containsKey(PageParam.PARAM_USER_PASSWORD)
                ? params.get(PageParam.PARAM_USER_PASSWORD).matches(PASSWORD_PATTERN) ? params.get(PageParam.PARAM_USER_PASSWORD)
                : ERROR_MESSAGE : ERROR_MESSAGE);
        params.put(PageParam.PARAM_USER_PHONE, params.containsKey(PageParam.PARAM_USER_PHONE)
                ? params.get(PageParam.PARAM_USER_PHONE).matches(PHONE_PATTERN) ? params.get(PageParam.PARAM_USER_PHONE)
                : ERROR_MESSAGE : ERROR_MESSAGE);
        return !params.values().contains(ERROR_MESSAGE);
    }

    public static boolean isValidUserUpdate(Map<String, String> params) {
        if (params.containsKey(PageParam.PARAM_USER_LASTNAME)) {
            params.put(PageParam.PARAM_USER_LASTNAME, params.get(PageParam.PARAM_USER_LASTNAME).
                    matches(LASTNAME_PATTERN) ? params.get(PageParam.PARAM_USER_LASTNAME) : ERROR_MESSAGE);
        }
        if (params.containsKey(PageParam.PARAM_USER_FIRSTNAME)) {
            params.put(PageParam.PARAM_USER_FIRSTNAME, params.get(PageParam.PARAM_USER_FIRSTNAME).
                    matches(FIRSTNAME_PATTERN) ? params.get(PageParam.PARAM_USER_FIRSTNAME) : ERROR_MESSAGE);
        }
        if (params.containsKey(PageParam.PARAM_USER_PHONE)) {
            params.put(PageParam.PARAM_USER_PHONE, params.get(PageParam.PARAM_USER_PHONE).matches(PHONE_PATTERN) ?
                    params.get(PageParam.PARAM_USER_PHONE) : ERROR_MESSAGE);
        }
        return !params.values().contains(ERROR_MESSAGE);
    }

    public static boolean isValidEventNew(Map<String, String> params) {
        params.put(PageParam.EVENT_DESCRIPTION, params.containsKey(PageParam.EVENT_DESCRIPTION) ?
                params.get(PageParam.EVENT_DESCRIPTION).matches(XSS_ATTACK) ?
                        ERROR_MESSAGE : params.get(PageParam.EVENT_DESCRIPTION) : ERROR_MESSAGE);
        params.put(PageParam.PARAM_EVENT_TYPE, params.containsKey(PageParam.PARAM_EVENT_TYPE) ?
                params.get(PageParam.PARAM_EVENT_TYPE) : ERROR_MESSAGE);
        params.put(PageParam.EVENT_SHORT_DESCRIPTION, params.containsKey(PageParam.EVENT_SHORT_DESCRIPTION)
                ? params.get(PageParam.EVENT_SHORT_DESCRIPTION).matches(XSS_ATTACK) ?
                ERROR_MESSAGE : params.get(PageParam.EVENT_SHORT_DESCRIPTION) : ERROR_MESSAGE);
        params.put(PageParam.PARAM_EVENT_ADD_NAME, params.containsKey(PageParam.PARAM_EVENT_ADD_NAME)
                ? params.get(PageParam.PARAM_EVENT_ADD_NAME).matches(XSS_ATTACK) ?
                ERROR_MESSAGE : params.get(PageParam.PARAM_EVENT_ADD_NAME) : ERROR_MESSAGE);
        params.put(PageParam.PARAM_MAIN_PICTURE_NAME, params.containsKey(PageParam.PARAM_MAIN_PICTURE_NAME)
                ? params.get(PageParam.PARAM_MAIN_PICTURE_NAME) : ERROR_MESSAGE);
        return !params.values().contains(ERROR_MESSAGE);
    }

    public static boolean isValidEventUpdate(Map<String, String> params) {
        if (params.containsKey(PageParam.EVENT_DESCRIPTION)) {
            params.put(PageParam.EVENT_DESCRIPTION, params.get(PageParam.EVENT_DESCRIPTION).
                    matches(XSS_ATTACK) ? ERROR_MESSAGE : params.get(PageParam.EVENT_DESCRIPTION));
        }
        if (params.containsKey(PageParam.EVENT_SHORT_DESCRIPTION)) {
            params.put(PageParam.EVENT_SHORT_DESCRIPTION, params.get(PageParam.EVENT_SHORT_DESCRIPTION).
                    matches(XSS_ATTACK) ? ERROR_MESSAGE : params.get(PageParam.EVENT_SHORT_DESCRIPTION));
        }
        if (params.containsKey(PageParam.PARAM_EVENT_NAME)) {
            params.put(PageParam.PARAM_EVENT_NAME, params.get(PageParam.PARAM_EVENT_NAME).
                    matches(XSS_ATTACK) ? ERROR_MESSAGE : params.get(PageParam.PARAM_EVENT_NAME));
        }
        return !params.values().contains(ERROR_MESSAGE);

    }

    public static boolean isValidMessage(String message) {
        return !message.matches(XSS_ATTACK);

    }

    public static boolean isValidEventDateNew(Map<String, String> params) {
       /* params.put(PageParam.PARAM_EVENT_DATE_TIME, params.containsKey(PageParam.PARAM_EVENT_DATE_TIME) ?
                params.get(PageParam.PARAM_EVENT_DATE_TIME).matches(XSS_ATTACK) ?
                        new String[]{ERROR_MESSAGE} : params.get(PageParam.PARAM_EVENT_DATE_TIME) : new String[]{ERROR_MESSAGE});
        params.put(PageParam.PARAM_COUNT_TICKETS, params.containsKey(PageParam.PARAM_COUNT_TICKETS) ?
                params.get(PageParam.PARAM_COUNT_TICKETS) : new String[]{ERROR_MESSAGE});*/
        return !params.values().contains(ERROR_MESSAGE);

    }

    public static boolean isValidCDataForChangePassword(Map<String, String> params) {
        params.put(PageParam.OLD_PASSWORD, params.containsKey(PageParam.OLD_PASSWORD)
                ? params.get(PageParam.OLD_PASSWORD).matches(PASSWORD_PATTERN) ? params.get(PageParam.OLD_PASSWORD)
                : ERROR_MESSAGE : ERROR_MESSAGE);
        params.put(PageParam.NEW_PASSWORD, params.containsKey(PageParam.NEW_PASSWORD)
                ? params.get(PageParam.NEW_PASSWORD).matches(PASSWORD_PATTERN) ? params.get(PageParam.NEW_PASSWORD)
                : ERROR_MESSAGE : ERROR_MESSAGE);
        params.put(PageParam.REPEATED_NEW_PASSWORD, params.containsKey(PageParam.REPEATED_NEW_PASSWORD)
                ? params.get(PageParam.REPEATED_NEW_PASSWORD).matches(PASSWORD_PATTERN) ?
                params.get(PageParam.REPEATED_NEW_PASSWORD) : ERROR_MESSAGE : ERROR_MESSAGE);
        return !params.values().contains(ERROR_MESSAGE);
    }

}
