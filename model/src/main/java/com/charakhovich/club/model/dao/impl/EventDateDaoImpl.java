package com.charakhovich.club.model.dao.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.EventDateDao;
import com.charakhovich.club.model.dao.SqlQuery;
import com.charakhovich.club.model.dao.TableColumnName;
import com.charakhovich.club.model.entity.*;
import com.charakhovich.club.model.exeption.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDateDaoImpl extends AbstractDao<EventDate> implements EventDateDao {

    @Override
    public List<EventDate> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<EventDate> findEntityById(long id) throws DaoException {
        Optional<EventDate> eventDateOptional = Optional.empty();
        Connection connection = this.connection;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_SCHEDULE_BY_ID);
            statement.setString(1, Ticket.State.PAID.toString());
            statement.setLong(2, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                EventDate eventDate = new EventDate();
                eventDate.setEventDateId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_ID));
                Timestamp t = resultSet.getTimestamp(TableColumnName.EVENT_DATE_DAO_EVENT_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                eventDate.setEventDateTime(localDateTime);
                eventDate.setEventId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_EVENT_ID));
                eventDate.setTicketCost(resultSet.getBigDecimal(TableColumnName.EVENT_DATE_DAO_TICKET_COST));
                eventDate.setTicketCount(resultSet.getInt(TableColumnName.EVENT_DATE_DAO_TICKET_COUNT));
                eventDate.setFreeTicketCount(resultSet.getInt(SqlQuery.COUNT_FREE_TICKETS));
                eventDateOptional=Optional.of(eventDate);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return eventDateOptional;
    }

    @Override
    public boolean delete(EventDate entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int create(EventDate entity) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_EVENT_DATE, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getEventId());
            Timestamp timestamp = Timestamp.valueOf(entity.getEventDateTime());
            statement.setTimestamp(2, timestamp);
            statement.setString(3, EventDate.State.ACTUAL.toString());
            statement.setInt(4, entity.getTicketCount());
            statement.setBigDecimal(5, entity.getTicketCost());
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            statementClose(statement);
        }
    }

    @Override
    public int update(EventDate entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<EventDate> findEventDates(long eventId) throws DaoException {
        List<EventDate> listEventDates = new ArrayList<>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_EVENT_DATES);
            statement.setLong(1, eventId);
            statement.setString(2, EventDate.State.ACTUAL.toString());
            statement.setString(3, Ticket.State.PAID.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                EventDate eventDate = new EventDate();
                eventDate.setEventDateId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_ID));
                Timestamp t = resultSet.getTimestamp(TableColumnName.EVENT_DATE_DAO_EVENT_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                eventDate.setEventDateTime(localDateTime);
                eventDate.setEventId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_EVENT_ID));
                eventDate.setTicketCost(resultSet.getBigDecimal(TableColumnName.EVENT_DATE_DAO_TICKET_COST));
                eventDate.setTicketCount(resultSet.getInt(TableColumnName.EVENT_DATE_DAO_EVENT_STATE));
                eventDate.setFreeTicketCount(resultSet.getInt(SqlQuery.COUNT_FREE_TICKETS));
                listEventDates.add(eventDate);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listEventDates;

    }

    @Override
    public List<EventDate> findEventDates(long eventId, Page page) throws DaoException {
        List<EventDate> listEventDates = new ArrayList<>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_EVENT_DATES_LIMIT_PAGE);
            statement.setLong(2, eventId);
            statement.setString(3, EventDate.State.ACTUAL.toString());
            statement.setString(1, Ticket.State.PAID.toString());
            statement.setInt(4, page.getFirst());
            statement.setInt(5, page.getMax());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next() && !resultSet.wasNull()) {
                EventDate eventDate = new EventDate();
                eventDate.setEventDateId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_ID));
                Timestamp t = resultSet.getTimestamp(TableColumnName.EVENT_DATE_DAO_EVENT_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                eventDate.setEventDateTime(localDateTime);
                eventDate.setEventId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_EVENT_ID));
                eventDate.setTicketCost(resultSet.getBigDecimal(TableColumnName.EVENT_DATE_DAO_TICKET_COST));
                eventDate.setState(EventDate.State.ACTUAL);
                eventDate.setFreeTicketCount(resultSet.getInt(SqlQuery.COUNT_FREE_TICKETS));
                eventDate.setTicketCount(resultSet.getInt(TableColumnName.EVENT_DATE_DAO_TICKET_COUNT));
                listEventDates.add(eventDate);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listEventDates;
    }

    @Override
    public List<EventDate> findEventDates(long eventId, LocalDate localDate) throws DaoException {
        List<EventDate> listEventDates = new ArrayList<>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_EVENT_DATES_BY_LOCALDATE);
            statement.setString(1, Ticket.State.PAID.toString());
            statement.setString(2, Ticket.State.BOOKED.toString());
            statement.setString(3, Ticket.State.PAID.toString());
            statement.setString(4, Ticket.State.BOOKED.toString());
            statement.setLong(5, eventId);
        //    statement.setString(6, EventDate.State.ACTUAL.toString());
            statement.setString(6, "%" + localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                EventDate eventDate = new EventDate();
                eventDate.setEventDateId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_ID));
                Timestamp t = resultSet.getTimestamp(TableColumnName.EVENT_DATE_DAO_EVENT_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                eventDate.setEventDateTime(localDateTime);
                eventDate.setEventId(resultSet.getLong(TableColumnName.EVENT_DATE_DAO_EVENT_ID));
                eventDate.setTicketCost(resultSet.getBigDecimal(TableColumnName.EVENT_DATE_DAO_TICKET_COST));
                eventDate.setTicketCount(resultSet.getInt(TableColumnName.EVENT_DATE_DAO_TICKET_COUNT));
                eventDate.setState(EventDate.State.valueOf(resultSet.getString(TableColumnName.EVENT_DATE_DAO_EVENT_STATE)));
                int freeTicketCount=resultSet.getInt(SqlQuery.COUNT_FREE_TICKETS);
                eventDate.setFreeTicketCount(freeTicketCount);
                listEventDates.add(eventDate);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listEventDates;
    }


    @Override
    public int count(long eventId) throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_EVENT_DATES);
            statement.setLong(1, eventId);
            statement.setString(2, EventDate.State.ACTUAL.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_EVENT_DATES);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }

    @Override
    public int countDates(long eventId) throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_DATES_BY_EVENTID);
            statement.setLong(1, eventId);
            statement.setString(2, EventDate.State.ACTUAL.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_EVENT_DATES);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }


    @Override
    public List<LocalDate> findEventDatesQuest(long eventId, Page page) throws DaoException {
        List<LocalDate> listLocalDates = new ArrayList<>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_DATES_BY_EVENTID_LIMIT_PAGE);
            statement.setLong(1, eventId);
            statement.setString(2, EventDate.State.ACTUAL.toString());
            statement.setString(3, EventDate.State.BOOKED.toString());
            statement.setInt(4, page.getFirst());
            statement.setInt(5, page.getMax());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next() && !resultSet.wasNull()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                String dateString = resultSet.getString("date_");
                LocalDate localDate = LocalDate.parse(dateString, formatter);
                listLocalDates.add(localDate);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listLocalDates;

    }
}
