package com.charakhovich.club.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class RequestContext {

    private HashMap<String, Object> reqAttrs;
    private HashMap<String, String> reqParams;
    private HashMap<String, Object> sessionAttrs;

    public RequestContext(HttpServletRequest req) {
        reqAttrs = new HashMap<>();
        reqParams = new HashMap<>();
        sessionAttrs = new HashMap<>();
        HttpSession session = req.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        Iterator<String> sessionAttributeNamesIterator = sessionAttributeNames.asIterator();
        while (sessionAttributeNamesIterator.hasNext()) {
            String attrName = sessionAttributeNamesIterator.next();
            reqAttrs.put(attrName, session.getAttribute(attrName));
        }
        Enumeration<String> reqAttributeNames = req.getAttributeNames();
        Iterator<String> reqAttributeNamesIterator = reqAttributeNames.asIterator();
        while (reqAttributeNamesIterator.hasNext()) {
            String attrName = reqAttributeNamesIterator.next();
            reqAttrs.put(attrName, req.getAttribute(attrName));
        }
        Enumeration<String> reqParameterNames = req.getParameterNames();
        Iterator<String> reqParameterNamesIterator = reqParameterNames.asIterator();
        while (reqParameterNamesIterator.hasNext()) {
            String attrName = reqParameterNamesIterator.next();
            reqParams.put(attrName, req.getParameter(attrName));
        }
    }

    public HashMap<String, Object> getReqAttrs() {
        return reqAttrs;
    }

    public HashMap<String, String> getReqParams() {
        return reqParams;
    }

    public HashMap<String, Object> getSessionAttrs() {
        return sessionAttrs;
    }
}
