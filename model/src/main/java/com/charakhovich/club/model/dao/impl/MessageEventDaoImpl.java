package com.charakhovich.club.model.dao.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.MessageEventDao;
import com.charakhovich.club.model.dao.SqlQuery;
import com.charakhovich.club.model.dao.TableColumnName;
import com.charakhovich.club.model.entity.*;
import com.charakhovich.club.model.exeption.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageEventDaoImpl extends AbstractDao<MessageEvent> implements MessageEventDao {
    @Override
    public List<MessageEvent> findMessagesEvent(long eventId) throws DaoException {
        List<MessageEvent> listMessages = new ArrayList<MessageEvent>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_MESSAGES_BY_EVENT);
            statement.setString(1, String.valueOf(eventId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MessageEvent messageEvent = new MessageEvent();
                Event event=new Event();
                event.setEventId(resultSet.getLong(TableColumnName.MESSAGE_DAO_EVENT_ID));
                messageEvent.setEvent(event);
                messageEvent.setMessageId(resultSet.getLong(TableColumnName.MESSAGE_DAO_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(TableColumnName.MESSAGE_DAO_USER_ID));
                messageEvent.setUser(user);
                Timestamp t = resultSet.getTimestamp(TableColumnName.MESSAGE_DAO_MODIFY_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                messageEvent.setModifyDate(localDateTime);
                messageEvent.setText(resultSet.getString(TableColumnName.MESSAGE_DAO_TEXT));
                listMessages.add(messageEvent);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listMessages;

    }

    @Override
    public List<MessageEvent> findMessagesEvent(long eventId, Page page) throws DaoException {
        List<MessageEvent> listMessages = new ArrayList<MessageEvent>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_MESSAGES_BY_EVENT_LIMIT_PAGE);
            statement.setLong(1, eventId);
            statement.setString(2, MessageEvent.State.ACTUAL.toString());
            statement.setInt(3, page.getFirst());
            statement.setInt(4, page.getMax());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MessageEvent messageEvent = new MessageEvent();
                Event event=new Event();
                event.setEventId(resultSet.getLong(TableColumnName.MESSAGE_DAO_EVENT_ID));
                messageEvent.setEvent(event);
                messageEvent.setMessageId(resultSet.getLong(TableColumnName.MESSAGE_DAO_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(TableColumnName.MESSAGE_DAO_USER_ID));
                messageEvent.setUser(user);
                Timestamp t = resultSet.getTimestamp(TableColumnName.MESSAGE_DAO_MODIFY_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                messageEvent.setModifyDate(localDateTime);
                messageEvent.setText(resultSet.getString(TableColumnName.MESSAGE_DAO_TEXT));
                listMessages.add(messageEvent);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listMessages;

    }

    @Override
    public List<MessageEvent> findMessagesEvent(MessageEvent.State state, Page page) throws DaoException {
        List<MessageEvent> listMessages = new ArrayList<MessageEvent>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_MESSAGES_BY_STATE_LIMIT_PAGE);
            statement.setString(1, state.toString());
            statement.setInt(2, page.getFirst());
            statement.setInt(3, page.getMax());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MessageEvent messageEvent = new MessageEvent();
                Event event=new Event();
                event.setEventId(resultSet.getLong(TableColumnName.MESSAGE_DAO_EVENT_ID));
                messageEvent.setEvent(event);
                messageEvent.setMessageId(resultSet.getLong(TableColumnName.MESSAGE_DAO_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(TableColumnName.MESSAGE_DAO_USER_ID));
                messageEvent.setUser(user);
                Timestamp t = resultSet.getTimestamp(TableColumnName.MESSAGE_DAO_MODIFY_DATE);
                LocalDateTime localDateTime = t.toLocalDateTime();
                messageEvent.setModifyDate(localDateTime);
                messageEvent.setText(resultSet.getString(TableColumnName.MESSAGE_DAO_TEXT));
                listMessages.add(messageEvent);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listMessages;
    }

    @Override
    public int countOfMessages(long eventId) throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_MESSAGES_BY_EVENT);
            statement.setLong(1, eventId);
            statement.setString(2, MessageEvent.State.ACTUAL.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_MESSAGES_BY_EVENT);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;

    }

    @Override
    public int countOfMessageByState(MessageEvent.State state) throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_MESSAGES_BY_STATE);
            statement.setString(1, state.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_MESSAGES_BY_EVENT);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;

    }

    @Override
    public boolean updateState(long messageId, MessageEvent.State state) throws DaoException {
        boolean isUpdate;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_MESSAGE_STATE);
            statement.setString(1, state.toString());
            statement.setLong(2, messageId);
            int amountOfRows = statement.executeUpdate();
            isUpdate = amountOfRows > 0;
        } catch (SQLException e) {
            throw new DaoException("SQL exception (request or table failed)", e);
        } finally {
            statementClose(statement);

        }
        return isUpdate;
    }

    @Override
    public List<MessageEvent> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<MessageEvent> findEntityById(long id) throws DaoException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean delete(MessageEvent entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int create(MessageEvent entity) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getEvent().getEventId());
            statement.setLong(2, entity.getUser().getUserId());
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(3, timestamp);
            statement.setString(4, entity.getText());
            statement.setString(5,entity.getState().toString());
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
    }

    @Override
    public int update(MessageEvent entity) {
        throw new UnsupportedOperationException();
    }

}
