package com.charakhovich.club.model.dao;

/**
 * The type Column name.
 * Is used for store the database's field's names.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class TableColumnName {
    public static final String USER_DAO_ID = "user_id";
    public static final String USER_DAO_LOGIN = "login";
    public static final String USER_DAO_PASSWORD = "password";
    public static final String USER_DAO_FIRSTNAME = "firstname";
    public static final String USER_DAO_LASTNAME = "lastname";
    public static final String USER_DAO_STATE = "state";
    public static final String USER_DAO_ROLE = "role";
    public static final String USER_DAO_PHONE = "phone";
    public static final String USER_DAO_PHOTO = "photo";
    public static final String USER_DAO_BALANCE = "balance";
    public static final String USER_DAO_VERIFICATION_CODE = "verification_code";

    public static final String EVENT_DAO_ID = "event_id";
    public static final String EVENT_DAO_TYPE_EVENT = "type";
    public static final String EVENT_DAO_NAME = "name";
    public static final String EVENT_DAO_DESCRIPTION = "description";
    public static final String EVENT_DAO_DURATION = "duration";
    public static final String EVENT_DAO_STATE = "state";
    public static final String EVENT_DAO_MODIFY_DATE = "modifydate";
    public static final String EVENT_DAO_SHORT_DESCRIPTION = "short_description";

    public static final String MESSAGE_DAO_ID = "message_id";
    public static final String MESSAGE_DAO_EVENT_ID = "event_id";
    public static final String MESSAGE_DAO_USER_ID = "user_id";
    public static final String MESSAGE_DAO_MODIFY_DATE = "modifydate";
    public static final String MESSAGE_DAO_TEXT = "text";

    public static final String PICTURE_DAO_ID = "picture_id";
    public static final String PICTURE_DAO_EVENT_ID = "event_id";
    public static final String PICTURE_DAO_MODIFY_DATE = "modifydate";
    public static final String PICTURE_DAO_NAME = "picture_name";
    public static final String PICTURE_DAO_STATE = "state";
    public static final String PICTURE_DAO_TYPE = "type";
    public static final String PICTURE_DAO_BLOB = "picture";

    public static final String EVENT_DATE_DAO_ID = "eventdate_id";
    public static final String EVENT_DATE_DAO_EVENT_ID = "event_id";
    public static final String EVENT_DATE_DAO_EVENT_DATE = "date";
    public static final String EVENT_DATE_DAO_EVENT_STATE = "state";
    public static final String EVENT_DATE_DAO_TICKET_COUNT = "ticket_count";
    public static final String EVENT_DATE_DAO_TICKET_COST = "ticket_cost";

    public static final String TICKET_DAO_ID = "ticket_id";
    public static final String TICKET_DAO_SCHEDULE_ID = "eventdate_id";
    public static final String TICKET_DAO_USER_ID = "user_id";
    public static final String TICKET_DAO_STATE = "ticket_state";
    public static final String TICKET_DAO_TICKET_COUNT = "ticket_count";

    public TableColumnName() {
    }
}

