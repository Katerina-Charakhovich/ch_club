package com.charakhovich.club.web.command;

/**
 * The type Router.
 * Uses for returning a type of going to page
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class Router {
    public enum Type {
        FORWARD,
        REDIRECT
    }

    private String page;
    private Type type;

    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
