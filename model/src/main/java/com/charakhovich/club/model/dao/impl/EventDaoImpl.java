package com.charakhovich.club.model.dao.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.EventDao;
import com.charakhovich.club.model.dao.SqlQuery;
import com.charakhovich.club.model.dao.TableColumnName;
import com.charakhovich.club.model.entity.*;
import com.charakhovich.club.model.exeption.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDaoImpl extends AbstractDao<Event> implements EventDao {
    private static final Logger Logger = LogManager.getLogger(EventDaoImpl.class);

    @Override
    public List<Event> findAll(Page page) throws DaoException {
        List<Event> events = new ArrayList<Event>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_ACTUAL_EVENTS);
            statement.setString(1, Event.State.ACTUAL.toString());
            statement.setInt(2, page.getFirst());
            statement.setInt(3, page.getMax());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt(TableColumnName.EVENT_DAO_ID));
                event.setEventType(Event.Type.valueOf(resultSet.getString(TableColumnName.EVENT_DAO_TYPE_EVENT)));
                event.setName(resultSet.getString(TableColumnName.EVENT_DAO_NAME));
                event.setDescription(resultSet.getString(TableColumnName.EVENT_DAO_DESCRIPTION));
                Timestamp t = resultSet.getTimestamp(TableColumnName.EVENT_DAO_MODIFY_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                event.setModifyDate(localDateTime);
                event.setShortDescription(resultSet.getString(TableColumnName.EVENT_DAO_SHORT_DESCRIPTION));
                event.setDuration(resultSet.getDouble(TableColumnName.EVENT_DAO_DURATION));
                events.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return events;
    }

    @Override
    public List<Event> findAll(Event.Type type, Page page) throws DaoException {

        List<Event> events = new ArrayList<Event>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_ACTUAL_EVENTS_BY_TYPE);
            statement.setString(1, Event.State.ACTUAL.toString());
            statement.setString(2, type.toString());
            statement.setInt(3, page.getFirst());
            statement.setInt(4, page.getMax());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt(TableColumnName.EVENT_DAO_ID));
                event.setEventType(Event.Type.valueOf(resultSet.getString(TableColumnName.EVENT_DAO_TYPE_EVENT)));
                event.setName(resultSet.getString(TableColumnName.EVENT_DAO_NAME));
                event.setDescription(resultSet.getString(TableColumnName.EVENT_DAO_DESCRIPTION));
                Timestamp t = resultSet.getTimestamp(TableColumnName.EVENT_DAO_MODIFY_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                event.setModifyDate(localDateTime);
                event.setShortDescription(resultSet.getString(TableColumnName.EVENT_DAO_SHORT_DESCRIPTION));
                event.setDuration(resultSet.getDouble(TableColumnName.EVENT_DAO_DURATION));
                events.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return events;
    }

    @Override
    public int count() throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_EVENTS);
            statement.setString(1, Event.State.ACTUAL.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_EVENTS);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }

    @Override
    public int count(Event.Type type) throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_EVENTS_BY_TYPE);
            statement.setString(1, Event.State.ACTUAL.toString());
            statement.setString(2, type.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_EVENTS);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }

    @Override
    public List<Event> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Event> findEntityById(long eventId) throws DaoException {
        Optional<Event> eventOptional = Optional.empty();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_EVENT_BY_ID);
            statement.setLong(1, eventId);
            statement.setString(2, Event.State.ACTUAL.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Event event = new Event();
                event = new Event();
                event.setEventId(resultSet.getInt(TableColumnName.EVENT_DAO_ID));
                event.setEventType(Event.Type.valueOf(resultSet.getString(TableColumnName.EVENT_DAO_TYPE_EVENT)));
                event.setName(resultSet.getString(TableColumnName.EVENT_DAO_NAME));
                event.setDescription(resultSet.getString(TableColumnName.EVENT_DAO_DESCRIPTION));
                event.setShortDescription(resultSet.getString(TableColumnName.EVENT_DAO_SHORT_DESCRIPTION));
                event.setDuration(resultSet.getDouble(TableColumnName.EVENT_DAO_DURATION));
                eventOptional = Optional.of(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return eventOptional;


    }

    @Override
    public boolean delete(Event entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int create(Event event) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        int key = -1;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_EVENT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, event.getEventType().toString());
            statement.setString(2, event.getName());
            statement.setString(3, event.getDescription());
            statement.setString(4, event.getShortDescription());
            statement.setString(5, event.getState().toString());
            statement.setDouble(6, event.getDuration());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
    }

    @Override
    public int update(Event event) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        int result = -1;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_EVENT);
            statement.setString(1, event.getName());
            statement.setString(2, event.getDescription());
            statement.setString(3, event.getShortDescription());
            statement.setLong(4, event.getEventId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }
}
