package com.charakhovich.club.web.util;


import com.charakhovich.club.web.command.ApplicationParam;

import javax.servlet.http.Cookie;

import static com.charakhovich.club.web.command.ApplicationParam.APPLICATION_DOMAIN;
import static com.charakhovich.club.web.command.ApplicationParam.APPLICATION_PATH;

/**
 * The type Cookie Handler
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class CookieHandler {
    /**
     * Create cookie
     *
     * @param name  the name cookie
     * @param value the value cookie
     * @return the Cookie
     */
    public static Cookie create(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(ApplicationParam.APPLICATION_PATH);
        cookie.setPath(ApplicationParam.APPLICATION_DOMAIN);
        return cookie;
    }

    /**
     * Create cookie
     *
     * @param cookie the erase cookie
     * @return the Cookie
     */

    public static Cookie erase(Cookie cookie) {
        cookie.setDomain(APPLICATION_DOMAIN);
        cookie.setPath(APPLICATION_PATH);
        cookie.setMaxAge(0);
        return cookie;
    }
}
