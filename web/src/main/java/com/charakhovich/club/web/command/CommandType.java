package com.charakhovich.club.web.command;

import com.charakhovich.club.web.command.impl.*;
import com.charakhovich.club.web.command.impl.navigation.*;
/**
 * The enum Command type.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public enum CommandType {
    ADD_MESSAGE(new AddMessageCommand()),
    ADMIN_USERS(new AdminUsersPageCommand()),
    ADMIN_MESSAGE_APPROVE(new AdminMessageApproveCommand()),
    ADMIN_MESSAGE_CANCEL(new AdminMessageCancelCommand()),
    ADMIN_MESSAGES(new AdminMessagePageCommand()),
    MAIN_PAGE_VIEW(new MainPageCommand()),
    CHANGE_LOCALE(new LocaleCommand()),
    CHANGE_USER_PHOTO(new ChangeUserPhotoCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    EVENT_ADD(new EventAddCommand()),
    ADMIN_EVENT_ADD_PAGE((req, resp) -> new Router(PagePath.EVENT_ADD, Router.Type.FORWARD)),
    ADMIN_ADD_QUEST_DATE(new AdminAddQuestDateCommand()),
    ADMIN_ADD_EVENT_DATE(new AdminAddEventDateCommand()),
    ADMIN_EVENT_EDIT(new AdminEventEditPageCommand()),
    ADMIN_EVENTS(new AdminEventsPageCommand()),
    EVENTS(new ViewEventsCommand()),
    TICKET_SALE(new TicketSaleCommand()),
    EVENT_TICKET_SALE(new EventTicketSalePageCommand()),
    EVENT_VIEW(new EventViewPageCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    EMPTY(new EmptyCommand()),
    ERROR_403((req, resp) -> new Router(PagePath.ERROR_403, Router.Type.FORWARD)),
    ERROR_500((req, resp) -> new Router(PagePath.ERROR_500, Router.Type.FORWARD)),
    ERROR_404((req, resp) -> new Router(PagePath.ERROR_404, Router.Type.FORWARD)),
    PAGINATION(new PaginationCommand()),
    PRIVATE_CABINET(new PrivateCabinetPageCommand()),
    REGISTRY_SUCCESS_PAGE((new RegistrySuccessPageCommand())),
    REGISTRY_PAGE((req, resp) -> new Router(PagePath.REGISTRY_PAGE, Router.Type.FORWARD)),
    REGISTRATION(new RegistrationCommand()),
    VERIFICATION_TOKEN(new VerificationUserCommand()),
    UPDATE_EVENT_INFO((new UpdateEventInfoCommand())),
    UPDATE_USER_INFO((new UpdateUserInfoCommand()));
    Command command;

    CommandType(Command command) {
        this.command = command;
    }
    /**
     * Gets command.
     * @return the command
     */
    public Command getCommand() {
        return command;
    }
}
