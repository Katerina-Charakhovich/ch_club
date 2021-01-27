package com.charakhovich.club.web.command;
import com.charakhovich.club.model.exeption.ServiceException;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface Command {
    Router execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException, ServletException, FileUploadException;
}
