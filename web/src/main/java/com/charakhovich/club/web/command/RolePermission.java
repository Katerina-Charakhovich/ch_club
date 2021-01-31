package com.charakhovich.club.web.command;

import java.util.EnumSet;
import java.util.Set;

import static com.charakhovich.club.web.command.CommandType.*;
/**
 * The {@code RolePermission} enum represents role permission.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public enum RolePermission {
    /**
     * Guest command role permission.
     */
    GUEST(
            EnumSet.of(
                    MAIN_PAGE_VIEW,
                    CHANGE_LOCALE,
                    EVENTS,
                    EVENT_TICKET_SALE,
                    EVENT_VIEW,
                    LOGIN,
                    PAGINATION,
                    REGISTRY_SUCCESS_PAGE,
                    REGISTRY_PAGE,
                    REGISTRATION,
                    VERIFICATION_TOKEN,
                    LOGOUT,
                    ERROR_403,
                    ERROR_500,
                    ERROR_404
            )),

    /**
     * Client command role permission.
     */
    USER(
            EnumSet.of(
                    ADD_MESSAGE,
                    MAIN_PAGE_VIEW,
                    CHANGE_LOCALE,
                    CHANGE_PASSWORD,
                    CHANGE_USER_PHOTO,
                    EVENTS,
                    TICKET_SALE,
                    EVENT_TICKET_SALE,
                    EVENT_VIEW,
                    LOGOUT,
                    PAGINATION,
                    PRIVATE_CABINET,
                    UPDATE_USER_INFO,
                    ERROR_403,
                    ERROR_500,
                    ERROR_404
            )),


    /**
     * Admin command role permission.
     */
    ADMIN(
            EnumSet.of(
                    ADMIN_ADD_BALANCE,
                    ADD_MESSAGE,
                    ADMIN_USERS,
                    ADMIN_USER_BLOCK,
                    ADMIN_USER_UNBLOCK,
                    ADMIN_MESSAGES,
                    ADMIN_MESSAGE_APPROVE,
                    ADMIN_MESSAGE_CANCEL,
                    MAIN_PAGE_VIEW,
                    CHANGE_LOCALE,
                    CHANGE_PASSWORD,
                    CHANGE_USER_PHOTO,
                    EVENT_ADD,
                    ADMIN_EVENT_ADD_PAGE,
                    ADMIN_ADD_QUEST_DATE,
                    ADMIN_ADD_EVENT_DATE,
                    ADMIN_EVENT_EDIT,
                    ADMIN_EVENTS,
                    EVENTS,
                    TICKET_SALE,
                    EVENT_TICKET_SALE,
                    EVENT_VIEW,
                    LOGOUT,
                    PAGINATION,
                    PRIVATE_CABINET,
                    UPDATE_USER_INFO,
                    UPDATE_EVENT_INFO,
                    ERROR_403,
                    ERROR_500,
                    ERROR_404
            ));

    private Set<CommandType> roleCommands;

    RolePermission(EnumSet<CommandType> roleCommands) {
        this.roleCommands = roleCommands;
    }

    /**
     * Get role commands set.
     *
     * @return the set
     */
    public Set<CommandType> getRoleCommands() {
        return this.roleCommands;
    }
}
