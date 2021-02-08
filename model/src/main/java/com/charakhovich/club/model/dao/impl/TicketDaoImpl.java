package com.charakhovich.club.model.dao.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.SqlQuery;
import com.charakhovich.club.model.dao.TableColumnName;
import com.charakhovich.club.model.dao.TicketDao;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.exeption.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.charakhovich.club.model.dao.SqlQuery.SELECT_TICKETS_BY_USER_ID;

public class TicketDaoImpl extends AbstractDao<Ticket> implements TicketDao {


    @Override
    public List<Ticket> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Ticket> findEntityById(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Ticket entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int create(Ticket entity) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getEventDateId());
            statement.setLong(2, entity.getUserId());
            statement.setInt(3, entity.getCountTicket());
            statement.setString(4, entity.getState().toString());
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
    }


    @Override
    public int update(Ticket entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Ticket> findTicketEvent(long eventId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<EventDate> findTicketEvent(long eventId, Page page) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Ticket> findTicketUser(long eventId) throws DaoException {
        List<Ticket> tickets = new ArrayList();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_TICKETS_BY_USER_ID);
            statement.setLong(1, eventId);
        /*    statement.setInt(2, page.getFirst());
            statement.setInt(3, page.getMax());*/
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getInt(TableColumnName.TICKET_DAO_ID));

                ticket.setEventDateId(resultSet.getLong(TableColumnName.TICKET_DAO_SCHEDULE_ID));
                ticket.setUserId(resultSet.getLong(TableColumnName.TICKET_DAO_USER_ID));
                ticket.setCountTicket(resultSet.getInt(TableColumnName.TICKET_DAO_TICKET_COUNT));
                ticket.setState(Ticket.State.valueOf(resultSet.getString(TableColumnName.TICKET_DAO_STATE)));
                Timestamp t = resultSet.getTimestamp(SqlQuery.TICKET_DATE);
                LocalDateTime  localDateTime= t.toLocalDateTime();
                ticket.setDateTime(localDateTime);
                ticket.setEventName(resultSet.getString(SqlQuery.EVENT_NAME));
                StringBuilder userFullName=new StringBuilder(resultSet.getString(SqlQuery.LASTNAME)).append(" ").
                        append(resultSet.getString(SqlQuery.FIRSTNAME));
                ticket.setUserName(userFullName.toString());
                tickets.add(ticket);

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return tickets;
    }

    @Override
    public int count(long eventId) throws DaoException {
        throw new UnsupportedOperationException();
    }
}