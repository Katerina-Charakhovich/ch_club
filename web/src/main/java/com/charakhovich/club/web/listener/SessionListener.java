package com.charakhovich.club.web.listener;

import com.charakhovich.club.web.command.PageAttribute;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_EN";
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(PageAttribute.LOCALE, DEFAULT_LOCALE);
    }
}
