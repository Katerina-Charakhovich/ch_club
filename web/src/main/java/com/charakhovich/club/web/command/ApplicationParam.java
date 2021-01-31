package com.charakhovich.club.web.command;
/**
 * The type default application parameters.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class ApplicationParam {

    public static final String CONTROLLER_REGEX = "/do/";
    public static final String ONE_PARAMETER_SPLIT = "?";
    public static final String SIGN_EQUALS = "=";
    public static final String MULTI_PARAMETER_SPLIT = "&";

    public static final int DEFAULT_PAGINATION_NUMBER  = 1;
    public static final int DEFAULT_COUNT_EVENT_FOR_VIEW=6;
    public static final int DEFAULT_COUNT_MESSAGE_EVENT_VIEW=5;
    public static final int DEFAULT_COUNT_VIEW_QUEST_DATES=7;
    public static final int DEFAULT_COUNT_TICKET=1;

    public static final String APPLICATION_DOMAIN="localhost";
    public static final String APPLICATION_PATH="/club";
    private ApplicationParam() {
    }
}
