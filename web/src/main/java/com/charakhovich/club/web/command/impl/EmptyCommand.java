package com.charakhovich.club.web.command.impl;

import com.charakhovich.club.web.command.Command;
import com.charakhovich.club.web.command.PagePath;
import com.charakhovich.club.web.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmptyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        return new Router(PagePath.REDIRECT_MAIN, Router.Type.REDIRECT);
    }
}
