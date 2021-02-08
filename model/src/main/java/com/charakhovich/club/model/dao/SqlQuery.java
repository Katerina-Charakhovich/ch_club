package com.charakhovich.club.model.dao;

public class SqlQuery {
    public static final String COUNT_USERS = "count_users";
    public static final String TICKET_DATE = "ticketDate";
    public static final String EVENT_NAME = "eventName";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "firstName";
    public static final String UPDATE_USER_PHOTO = "UPDATE ch_user " +
            "SET photo = ? " +
            "WHERE user_id = ?";
    public static final String UPDATE_USER_STATE = "UPDATE ch_user SET state = ? WHERE user_id = ?";
    public static final String UPDATE_USER_PASSWORD = "UPDATE ch_user " +
            "SET password =? " +
            "WHERE user_id =?";

    public static final String SELECT_PASSWORD_BY_LOGIN =
            "SELECT password FROM ch_user where login=?";
    public static final String SELECT_VERIFICATION_CODE_BY_LOGIN =
            "SELECT verification_code FROM ch_user where login=?";
    public static final String SELECT_USER_BY_LOGIN =
            "SELECT user_id, login,firstname,lastname,role, state, photo,phone,balance FROM ch_user where login=?";
    public static final String DELETE_USER_BY_ID =
            "DELETE FROM ch_user where user_id=? ";
    public static final String SELECT_USER_BY_ID =
            "SELECT user_id, login,firstname,lastname,role, state, photo, phone,balance FROM ch_user where user_id=? ";

    public static final String SELECT_USERS_BY_ROLE_LIMIT_PAGE =
            "SELECT user_id, login,firstname,lastname,role, state, photo, phone,balance FROM ch_user where role=? and  state in (?,?) " +
                    "order by lastname LIMIT ?,?";
    public static final String INSERT_USER = "INSERT INTO ch_user (login,  " +
            " lastname,firstname,phone,role) " +
            "VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_USER_WITH_PASSWORD = "INSERT INTO ch_user (login,  " +
            " lastname, firstname,phone,role,state,password,verification_code) " +
            "VALUES (?, ?, ?, ?, ?,?,?,?)";
    public static final String SELECT_COUNT_USERS_BY_ROLE = "SELECT COUNT(*) as count_users FROM ch_user where role=? " +
            "and state in (?,?)";
    public static final String UPDATE_USER =
            "UPDATE ch_user SET firstname=?,lastname=?,phone = ?WHERE (user_id =?)";
    public static final String UPDATE_USER_BALANCE_MINUS =
            "UPDATE ch_user SET balance =balance-? WHERE user_id =?";
    public static final String UPDATE_USER_BALANCE_ADD =
            "UPDATE ch_user SET balance =balance+? WHERE user_id =?";

    public static final String INSERT_TICKET = "INSERT INTO ch_ticket (eventdate_id,user_id,count,ticket_state) VALUES (?, ?, ?, ?)";
    public static final String SELECT_TICKETS_BY_USER_ID =
            "SELECT t.ticket_id,t.eventdate_id,t.user_id,t.count,t.ticket_state,t1.date as ticketDate, t2.name as eventName,u.lastname as lastName, u.firstname as firstName FROM ch_ticket t\n" +
                    "LEFT JOIN ch_user u on u.user_id=t.user_id " +
                    "LEFT JOIN ch_eventdate t1 on t.eventdate_id=t1.eventdate_id " +
                    "LEFT JOIN ch_event t2 on t1.event_id=t2.event_id " +
                    "WHERE t.user_id=? " ;
    public static final String SELECT_PICTURE_BY_EVENT_BY_TYPE =
            "select t.picture_id , t.type,t.picture from ch_picture t" +
                    " where t.event_id=? and t.state=? and t.type=?";
    public static final String INSERT_PICTURE = "INSERT INTO ch_picture(picture_name, picture_type, modifydate, actflag) VALUES (?, ?, ?, ?)";
    public static final String INSERT_PICTURE_BLOB = "INSERT INTO ch_picture(event_id,type,picture) VALUES (?, ?, ?)";
    public static final String INSERT_PICTURE_BY_EVENT_ID = "INSERT INTO ch_eventpicture (picture_id, event_id," +
            " modifydate, actflag) VALUES (?,?, ?, ?)";
    public static final String COUNT_MESSAGES_BY_EVENT = "count_message";

    public static final String SELECT_MESSAGES_BY_EVENT =
            "SELECT message_id, user_id, event_id, text, modifydate FROM ch_message where event_id=? and state=? ORDER BY modifydate";
    public static final String SELECT_MESSAGES_BY_EVENT_LIMIT_PAGE =
            "SELECT message_id, user_id, event_id, text, modifydate FROM ch_message where event_id=?  and state=? ORDER BY modifydate " +
                    "LIMIT ?,?";

    public static final String SELECT_MESSAGES_BY_STATE_LIMIT_PAGE =
            "SELECT message_id, user_id, event_id, text, modifydate FROM ch_message where state=? ORDER BY modifydate " +
                    "LIMIT ?,?";
    public static final String UPDATE_MESSAGE_STATE = " UPDATE ch_message SET state = ? WHERE message_id = ?";
    public static final String INSERT_MESSAGE = "INSERT INTO ch_message (event_id,user_id, modifydate, text,state )" +
            " VALUES (?, ?,?,?,?)";
    public static final String SELECT_COUNT_MESSAGES_BY_EVENT = "SELECT COUNT(*) as count_message FROM ch_message where event_id=? and state=?";
    public static final String SELECT_COUNT_MESSAGES_BY_STATE = "SELECT COUNT(*) as count_message FROM ch_message where state=?";

    public static final String COUNT_EVENTS = "count_events";
    public static final String SELECT_ACTUAL_EVENTS =
            "select t.event_id,t.type,t.name,t.description,t.state," +
                    "t.modifydate,t.short_description,t.duration" +
                    " from ch_event t" +
                    " where t.state=? " +
                    " order by t.modifydate desc LIMIT ?,?";

    public static final String SELECT_ACTUAL_EVENTS_BY_TYPE =
            "select t.event_id,t.type,t.name,t.description,t.state," +
                    "t.modifydate,t.short_description,t.duration" +
                    " from ch_event t" +
                    " where t.state=? and t.type=?" +
                    " order by t.modifydate desc LIMIT ?,?";

    public static final String INSERT_EVENT =
            "INSERT INTO ch_event (type,name,description,short_description,state,duration )  VALUES(?,?,?,?,?,?)";
    public static final String SELECT_EVENT_BY_ID =
            "select t.event_id,t.type,t.name,t.description,t.short_description,t.state,t.duration from ch_event t" +
                    " where t.event_id=? and t.state=? ";
    public static final String SELECT_COUNT_EVENTS = "SELECT COUNT(*) as count_events FROM ch_event where state=?";
    public static final String SELECT_COUNT_EVENTS_BY_TYPE = "SELECT COUNT(*) as count_events FROM ch_event t where t.state=? and t.type=?";
    public static final String UPDATE_EVENT =
            "UPDATE ch_event SET name=?,description=?,short_description = ? WHERE (event_id =?)";
    public static final String COUNT_EVENT_DATES = "count_dates";
    public static final String COUNT_FREE_TICKETS = "free_tickets";

    public static final String SELECT_EVENT_DATES_LIMIT_PAGE =
            "SELECT t.eventdate_id, t.event_id, t.date, t.state,t.ticket_count,t.ticket_cost, t.ticket_count- " +
                    "(CASE " +
                    "WHEN t1.ticket_state =? THEN SUM(t1.count) " +
                    "ELSE 0 " +
                    "END)" +
                    "as free_tickets " +
                    "FROM ch_eventdate t left join ch_ticket t1 on t.eventdate_id=t1.eventdate_id " +
                    "where t.event_id=? and t.state=? " +
                    "  group by t.eventdate_id LIMIT ?,?";

    public static final String SELECT_EVENT_DATES_BY_LOCALDATE =
            "SELECT t.eventdate_id, t.event_id, t.date, t.state,t.ticket_count,t.ticket_cost, t.ticket_count-" +
                    "(CASE " +
                    "WHEN EXISTS(SELECT t1.count from ch_ticket t1 where t1.ticket_state in (?, ?) and t1.eventdate_id =t.eventdate_id) " +
                    "THEN (SELECT SUM(t1.count) from ch_ticket t1 where t1.ticket_state in (?, ?) and t1.eventdate_id =t.eventdate_id) " +
                    "ELSE 0 " +
                    "END)" +
                    "as free_tickets " +
                    "FROM ch_eventdate t  " +
                    "where t.event_id=? and t.date like ? order by t.date asc ";

    public static final String SELECT_SCHEDULE_BY_ID =
            "SELECT t.eventdate_id, t.event_id, t.date, t.state,t.ticket_count,t.ticket_cost, t.ticket_count- " +
                    "(CASE " +
                    "WHEN t1.ticket_state =? THEN SUM(t1.count) " +
                    "ELSE 0 " +
                    "END)" +
                    "as free_tickets " +
                    "FROM ch_eventdate t left join ch_ticket t1 on t.eventdate_id=t1.eventdate_id " +
                    "where t.eventdate_id=?";
    public static final String SELECT_EVENT_DATES =
            "SELECT t.eventdate_id, t.event_id, t.date, t.state,t.ticket_count,t.ticket_cost, t.ticket_count- IFNULL (SUM(t1.count), 0) as free_tickets " +
                    "t.event_id=? and t.event_state=? and IFNULL (t1.ticket_state =?, null ) " +
                    "  group by t.eventdate_id ";

    public static final String INSERT_EVENT_DATE = "INSERT INTO ch_eventdate (event_id,date, state, ticket_count,ticket_cost)" +
            " VALUES (?,?,?,?,?)";
    public static final String SELECT_COUNT_EVENT_DATES = "SELECT COUNT(*) as count_dates FROM " +
            "ch_eventdate where event_id=? and state=?";
    public static final String SELECT_DATES_BY_EVENTID_LIMIT_PAGE =
            "SELECT distinct DATE_FORMAT(t.date,'%d/%m/%y') as date_  FROM ch_eventdate t " +
                    "where t.event_id=? and t.state in (?,?) and t.date>= now() " +
                    "order by t.date asc LIMIT ?,?";
    public static final String SELECT_DATES_BY_EVENTID =
            "SELECT distinct DATE_FORMAT(t.date,'%d/%m/%y') as date_  FROM ch_eventdate t " +
                    "where t.event_id=? and t.state in (?,?) and t.date>= now() " +
                    "order by t.date asc";
    public static final String SELECT_COUNT_DATES_BY_EVENTID = "SELECT COUNT(*) as count_dates FROM " +
            "(SELECT distinct DATE_FORMAT(t.date,'%d/%m/%y') as date_ " +
            "FROM ch_eventdate t  where t.event_id=? and t.state in (?) " +
            "and t.date>= now()  order by t.date asc ) as dates";
    public static final String SELECT_COUNT_DATES_BY_EVENTID_AVAILABLE = "SELECT COUNT(*) as count_dates FROM " +
            "(SELECT distinct DATE_FORMAT(t.date,'%d/%m/%y') as date_ " +
            "FROM ch_eventdate t  where t.event_id=? and t.state in (?) " +
            "and t.date>= now()  order by t.date asc ) as dates";

    private SqlQuery() {
    }
}
