package com.charakhovich.club.web.controller;

import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.pool.ConnectionPool;
import com.charakhovich.club.web.command.*;
import com.charakhovich.club.web.command.factory.ActionFactory;
import com.charakhovich.club.web.command.impl.EmptyCommand;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/do/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {

    static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Command command = ActionFactory.defineCommand(req);
        if (command instanceof EmptyCommand) {
            resp.sendRedirect(req.getContextPath() + PagePath.REDIRECT_ERROR_404);
            return;
        }
        Router router;
        try {
            router = command.execute(req, resp);

            if (router.getPage() == null) {
                router.setPage(PagePath.MAIN);
            }
            req.getSession().setAttribute(PageAttribute.CURRENT_PAGE, router.getPage());
            if (Router.Type.FORWARD.equals(router.getType())) {
                RequestDispatcher dispatcher = req.getRequestDispatcher(router.getPage());
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + router.getPage());
            }
            return;
        } catch (ServiceException | FileUploadException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + PagePath.REDIRECT_ERROR_500);
        }
    }


    @Override
    public void init() throws ServletException {
        super.init();
        logger.log(Level.ERROR, "Init pool");
        ConnectionPool connectionPool = ConnectionPool.getInstance();

    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}


