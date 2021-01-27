package com.charakhovich.club.model.dao.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.SqlQuery;
import com.charakhovich.club.model.dao.TicketDao;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.exeption.DaoException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

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
            statement.setLong(1, entity.getScheduleId());
            statement.setLong(2, entity.getUserId());
            statement.setInt(3, entity.getCountTicket());
            statement.setString(4, entity.getState().toString());
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
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
    public int count(long eventId) throws DaoException {
        throw new UnsupportedOperationException();
    }
}