package com.charakhovich.club.web.command.factory;

import com.charakhovich.club.web.command.Command;
import com.charakhovich.club.web.command.CommandType;
import com.charakhovich.club.web.command.PageParam;
import com.charakhovich.club.web.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionFactory {
    private static Logger logger = LogManager.getLogger(ActionFactory.class);
    private static final String CONTROLLER_REGEX = "/do/";
    private static final String QUESTION_MARK = "?";
    private static final String EMPTY_VALUE = "";
    private static final String PATH_START_REGEX = "/do.+";

    public static Command defineCommand(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String commandString = Optional.ofNullable(request.getParameter(PageParam.PARAM_COMMAND)).orElse(EMPTY_VALUE);
        CommandType cmdType;
        Command command;
        if (!commandString.isBlank()) {
            try {
                cmdType = CommandType.valueOf(commandString.toUpperCase());
                command = cmdType.getCommand();
            } catch (IllegalArgumentException e) {
                logger.error("Wrong command parameter: ", e);
                return CommandType.EMPTY.getCommand();
            }
        } else {
            if (requestUri == null || requestUri.isEmpty()) {
                return CommandType.EMPTY.getCommand();
            }
            requestUri = subStringPathWithRegex(requestUri);
            try {
                cmdType = CommandType.valueOf(requestUri.toUpperCase());

                command = cmdType.getCommand();
            } catch (IllegalArgumentException e) {
                logger.error("Wrong command parameter: ", e);
                return CommandType.EMPTY.getCommand();
            }
        }
        return command;
    }

    public static CommandType defineCommandType(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        request.getParameterMap();
        String commandString = Optional.ofNullable(request.getParameter(PageParam.PARAM_COMMAND)).orElse(EMPTY_VALUE);
        CommandType cmdType;
        try {
            if (!commandString.isBlank()) {
                cmdType = CommandType.valueOf(commandString.toUpperCase());
            } else {
                if (requestUri == null || requestUri.isEmpty()) {
                    return CommandType.EMPTY;
                }
                requestUri = subStringPathWithRegex(requestUri);
                cmdType = CommandType.valueOf(requestUri.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            logger.error("Wrong command parameter: ", e);
            return CommandType.EMPTY;
        }
        return cmdType;
    }


    public static String subStringPathWithRegex(String url) {
        Pattern pattern = Pattern.compile(PATH_START_REGEX);
        String path = null;
        if (url != null) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                path = matcher.group(0);
            } else {
                path = PagePath.MAIN;
            }
        }
        String str1 = path.replaceAll(CONTROLLER_REGEX, EMPTY_VALUE);
        int indexEnd = url.indexOf(QUESTION_MARK);
        return indexEnd == -1 ? (str1) : str1.substring(0, indexEnd);
    }
}
