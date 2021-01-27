package com.charakhovich.club.web.util;

import com.charakhovich.club.web.command.PageAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The type Pagination tag.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */

public class PaginationTag extends TagSupport {
    public static final String PAGINATION_ELEM_TABLE_OPEN = "<table>";
    public static final String PAGINATION_ELEM_TABLE_CLOSE = "</table>";
    public static final String PAGINATION_ELEM_TR_OPEN = "<tr>";
    public static final String PAGINATION_ELEM_TR_CLOSE = "</tr>";
    public static final String PAGINATION_STYLE_CLASS = "<ul class=\"pagination\">";
    public static final String PAGINATION_LI_OPEN = "<li>";
    public static final String PAGINATION_LI_CLOSE = "</li>";
    public static final String PAGINATION_A_OPEN = "<a href=\"";
    public static final String PAGINATION_A_CLOSE = "</a>";
    public static final String PAGINATION_A_CLASS = "class=\"active\"";
    public static final String PAGINATION_BUTTON_PREVIOUS = "Previous";
    public static final String PAGINATION_BUTTON_NEXT = "Next";
    public static final String PAGINATION_QUOTES = "\"";
    public static final String PAGINATION_COMMAND = "/do?command=pagination&page=";
    public static final String PAGINATION_CLOSE_BRACKET = ">";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String commandString = (String) request.getSession().getAttribute(PageAttribute.CURRENT_COMMAND);

        String pageContextRequestContextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
        int numberPage = (Integer) request.getAttribute(PageAttribute.PAGINATION_NUMBER_PAGE);
        int countPages = (Integer) request.getAttribute(PageAttribute.PAGINATION_COUNT_PAGES);
        if (countPages != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PAGINATION_ELEM_TABLE_OPEN).append(PAGINATION_ELEM_TR_OPEN).append(PAGINATION_STYLE_CLASS);
            if (numberPage != 1) {
                stringBuilder.append(PAGINATION_LI_OPEN).append(PAGINATION_A_OPEN).append(pageContextRequestContextPath).append(PAGINATION_COMMAND)
                        .append(numberPage - 1).append(PAGINATION_QUOTES).append(PAGINATION_CLOSE_BRACKET).append(PAGINATION_BUTTON_PREVIOUS)
                        .append(PAGINATION_A_CLOSE).append(PAGINATION_LI_CLOSE);
            }
            for (int i = 1; i < countPages + 1; i++) {
                if (i == numberPage) {
                    stringBuilder.append(PAGINATION_LI_OPEN).append(PAGINATION_A_OPEN).append(pageContextRequestContextPath).append(PAGINATION_COMMAND)
                            .append(i).append(PAGINATION_QUOTES).append(PAGINATION_A_CLASS).append(PAGINATION_CLOSE_BRACKET).append(i)
                            .append(PAGINATION_A_CLOSE).append(PAGINATION_LI_CLOSE);
                } else {
                    stringBuilder.append(PAGINATION_LI_OPEN).append(PAGINATION_A_OPEN).append(pageContextRequestContextPath).append(PAGINATION_COMMAND)
                            .append(i).append(PAGINATION_QUOTES).append(PAGINATION_CLOSE_BRACKET).append(i)
                            .append(PAGINATION_A_CLOSE).append(PAGINATION_LI_CLOSE);
                }
            }
            if (numberPage != countPages && countPages > 0) {
                stringBuilder.append(PAGINATION_LI_OPEN).append(PAGINATION_A_OPEN).append(pageContextRequestContextPath)
                        .append(PAGINATION_COMMAND)
                        .append(numberPage + 1).append(PAGINATION_QUOTES).append(PAGINATION_CLOSE_BRACKET).append(PAGINATION_BUTTON_NEXT)
                        .append(PAGINATION_A_CLOSE).append(PAGINATION_LI_CLOSE);
            }
            stringBuilder.append(PAGINATION_ELEM_TR_CLOSE).append(PAGINATION_ELEM_TABLE_CLOSE);
            try {
                JspWriter out = pageContext.getOut();
                out.write(stringBuilder.toString());
            } catch (
                    IOException e) {
                throw new JspException(e.getMessage());
            }
        }
        return SKIP_BODY;
    }
}




