package com.charakhovich.club.web.util;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.web.command.PageAttribute;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
/**
 * The type Pagination tag.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class UserTag extends BodyTagSupport {
    private static final String DIV_START = " <div class=\"col-md-12 col-xl-12 user\">" +
            "<div id=\"user_info\" style=\"float:right;\">";
    private static final String LI_START ="<i>";
    private static final String LI_END="</i>";
    private static final String DIV_END ="</div> <div class=\"clearfix\"></div></div>";

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        User authUser = (User) session.getAttribute(PageAttribute.AUTH_USER);
        if (authUser != null) {
            String userFullName = authUser.fullName();
            String role = authUser.getRole().toString();
            String telephone = authUser.getPhone();
            StringBuilder result = new StringBuilder(DIV_START).append(LI_START).append(userFullName).append(LI_END)
                    .append(LI_START).append(role).append(LI_END).append(LI_START).append(LI_END).append(DIV_END);
            try {
                JspWriter out = pageContext.getOut();
                out.write(result.toString());
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
        }
        return SKIP_BODY;
    }
}


