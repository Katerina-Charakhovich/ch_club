package com.charakhovich.club.web.command;

/**
 * The type Page Path of request.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class PagePath {
    public static final String REDIRECT_MAIN = "/do/MAIN_PAGE_VIEW";
    public static final String REDIRECT_ADMIN_EVENT_VIEW = "/do/ADMIN_EVENT_EDIT";
    public static final String REDIRECT_EVENT_VIEW = "/do/EVENT_VIEW";
    public static final String REDIRECT_ADMIN_EVENTS = "/do/ADMIN_EVENTS";
    public static final String REDIRECT_ADMIN_MESSAGES = "/do/ADMIN_MESSAGES";
    public static final String REDIRECT_EVENT_TICKET_SALE = "/do/EVENT_TICKET_SALE";
    public static final String REDIRECT_REGISTRY_PAGE = "/do/EVENT_TICKET_SALE";
    public static final String REDIRECT_REGISTRY_SUCCESS_PAGE = "/do/REGISTRY_SUCCESS_PAGE";
    public static final String REDIRECT_PRIVATE_CABINET_PAGE = "/do/PRIVATE_CABINET";
    public static final String REDIRECT_ADMIN_EVENT_EDIT = "/do/ADMIN_EVENT_EDIT";
    public static final String REDIRECT_ERROR_500 = "/do/ERROR_500";
    public static final String REDIRECT_ERROR_404 = "/do/ERROR_404";
    public static final String REDIRECT_ERROR_403 = "/do/ERROR_403";
    public static final String REDIRECT_ADMIN_USERS = "/do/ADMIN_USERS";


    public static final String MAIN = "/WEB-INF/pages/main.jsp";
    public static final String EVENTS = "/WEB-INF/pages/events.jsp";
    public static final String EVENT_ADD = "/WEB-INF/pages/admin/admin_event_add.jsp";

    public static final String ADMIN_EVENTS = "/WEB-INF/pages/admin/admin_events.jsp";
    public static final String ADMIN_EVENT_EDIT = "/WEB-INF/pages/admin/admin_event_edit.jsp";
    public static final String ADMIN_MESSAGES = "/WEB-INF/pages/admin/admin_messages.jsp";
    public static final String ADMIN_USERS = "/WEB-INF/pages/admin/admin_users.jsp";
    public static final String EVENT_VIEW = "/WEB-INF/pages/event_view.jsp";
    public static final String ERROR_404 = "/WEB-INF/pages/error404.jsp";
    public static final String ERROR_403 = "/WEB-INF/pages/error403.jsp";
    public static final String ERROR_500 = "/WEB-INF/pages/error500.jsp";
    public static final String ADMIN_EVENT_VIEW = "/WEB-INF/pages/admin/admin_events.jsp";
    public static final String PRIVATE_CABINET = "/WEB-INF/pages/private_cabinet.jsp";
    public static final String EVENT_TICKET_SALE = "/WEB-INF/pages/ticket_sale.jsp";
    public static final String SUCCESS_PAGE = "/WEB-INF/pages/success_page.jsp";

    public static final String REGISTRY_PAGE = "/WEB-INF/pages/registration.jsp";


    private PagePath() {
    }
}
