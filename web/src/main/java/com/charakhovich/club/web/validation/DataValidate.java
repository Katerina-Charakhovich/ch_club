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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.charakhovich.club.web.command.PageAttribute.*;

public class DataValidate {
    static final Logger logger = LogManager.getLogger(DataValidate.class);
    public static final String ERROR_MESSAGE = "error";
    public static final String XSS_ATTACK = "<[/]?script[\\s\\S]*?>";

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
    private static final double MIN_DURATION_EVENT = 0.5;
    private static final double MAX_DURATION_EVENT = 3.0;


    public static boolean isValidEventDate(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate.compareTo(LocalDate.now().plusDays(1)) > -1 ? true : false;
    }

    public static boolean isValidEventDate(LocalDate localDate) {
        return localDate.compareTo(LocalDate.now().plusDays(1)) > -1 ? true : false;
    }

    public static boolean isValidCostTicket(BigDecimal cost) {
        return (cost.compareTo(MIN_COST) > -1) && (cost.compareTo(MAX_COST) < 1);
    }

    public static boolean isValidCountTicket(int count) {
        return (count >= MIN_COUNT) && (count <= MAX_COUNT);
    }

    public static boolean isValidEventTime(LocalDateTime localDateTime) {
        LocalTime localTime = localDateTime.toLocalTime();
        return (localTime.compareTo(MIN_LOCAL_TIME) > -1) && (localTime.compareTo(MAX_LOCAL_TIME) < 1);
    }

    public static boolean isValidEventTime(LocalTime localTime) {
        return (localTime.compareTo(MIN_LOCAL_TIME) > -1) && (localTime.compareTo(MAX_LOCAL_TIME) < 1);
    }

    public static boolean isAddBalanceAmountValid(BigDecimal amount) {
        return (amount.compareTo(MIN_ADD_BALANCE_AMOUNT) > -1) && (amount.compareTo(MAX_ADD_BALANCE_AMOUNT) < 1);
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
        params.put(PageParam.EVENT_DESCRIPTION, params.containsKey(PageParam.EVENT_DESCRIPTION)
                ? isXssAttack(params.get(PageParam.EVENT_DESCRIPTION))
                || params.get(PageParam.EVENT_DESCRIPTION).trim().isBlank()
                ? ERROR_MESSAGE
                : params.get(PageParam.EVENT_DESCRIPTION).trim()
                : ERROR_MESSAGE);
        params.put(PageParam.PARAM_EVENT_TYPE, params.containsKey(PageParam.PARAM_EVENT_TYPE) ?
                params.get(PageParam.PARAM_EVENT_TYPE) : ERROR_MESSAGE);
        params.put(PageParam.EVENT_SHORT_DESCRIPTION, params.containsKey(PageParam.EVENT_SHORT_DESCRIPTION)
                ? isXssAttack(params.get(PageParam.EVENT_SHORT_DESCRIPTION))
                || params.get(PageParam.EVENT_SHORT_DESCRIPTION).trim().isBlank()
                ? ERROR_MESSAGE
                : params.get(PageParam.EVENT_SHORT_DESCRIPTION).trim()
                : ERROR_MESSAGE);
        params.put(PageParam.PARAM_EVENT_ADD_NAME, params.containsKey(PageParam.PARAM_EVENT_ADD_NAME)
                ? isXssAttack(params.get(PageParam.PARAM_EVENT_ADD_NAME))
                || params.get(PageParam.PARAM_EVENT_ADD_NAME).trim().isBlank()
                ? ERROR_MESSAGE : params.get(PageParam.PARAM_EVENT_ADD_NAME).trim()
                : ERROR_MESSAGE);
        params.put(PageParam.PARAM_EVENT_DURATION, params.containsKey(PageParam.PARAM_EVENT_DURATION)
                ? isValidDurationEvent(Double.parseDouble(params.get(PageParam.PARAM_EVENT_DURATION)))
                ? params.get(PageParam.PARAM_EVENT_DURATION)
                : ERROR_MESSAGE
                : ERROR_MESSAGE);
        return !params.values().contains(ERROR_MESSAGE);
    }

    public static boolean isValidEventUpdate(Map<String, String> params) {
        if (params.containsKey(PageParam.EVENT_DESCRIPTION)) {
            params.put(PageParam.EVENT_DESCRIPTION, isXssAttack(params.get(PageParam.EVENT_DESCRIPTION))
                    || params.get(PageParam.EVENT_DESCRIPTION).trim().isBlank()
                    ? ERROR_MESSAGE
                    : params.get(PageParam.EVENT_DESCRIPTION).trim());
        }
        if (params.containsKey(PageParam.EVENT_SHORT_DESCRIPTION)) {
            params.put(PageParam.EVENT_SHORT_DESCRIPTION, isXssAttack(params.get(PageParam.EVENT_SHORT_DESCRIPTION))
                    || params.get(PageParam.EVENT_SHORT_DESCRIPTION).trim().isBlank()
                    ? ERROR_MESSAGE
                    : params.get(PageParam.EVENT_SHORT_DESCRIPTION).trim());
        }
        if (params.containsKey(PageParam.PARAM_EVENT_NAME)) {
            params.put(PageParam.PARAM_EVENT_NAME, isXssAttack(params.get(PageParam.PARAM_EVENT_NAME))
                    || params.get(PageParam.PARAM_EVENT_NAME).trim().isBlank()
                    ? ERROR_MESSAGE
                    : params.get(PageParam.PARAM_EVENT_NAME).trim());
        }
        return !params.values().contains(ERROR_MESSAGE);

    }

    public static boolean isXssAttack(String message) {
        Pattern pattern=Pattern.compile(XSS_ATTACK);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();

    }

    public static boolean isValidMessage(String message) {
        return !isXssAttack(message) && !message.trim().isBlank();

    }

    public static boolean isValidDataForChangePassword(Map<String, String> params) {
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

    public static boolean isValidDurationEvent(double duration) {
        return duration >= MIN_DURATION_EVENT && duration <= MAX_DURATION_EVENT;

    }
}
