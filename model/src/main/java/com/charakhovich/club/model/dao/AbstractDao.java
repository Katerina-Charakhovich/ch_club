package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Entity;
import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.exeption.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * The type Abstract dao.
 *
 * @param <T> the type parameter
 * @author Katerina Charakhovich
 * @version 1.0
 */
public abstract class AbstractDao<T extends Entity> {
    private static final Logger logger = LogManager.getLogger(AbstractDao.class);
    protected Connection connection;

    public abstract List<T> findAll() throws DaoException;

    /**
     * Finds entity by entity id
     *
     * @param id the entity id
     * @return the t
     * @throws DaoException the sql exception
     */
    public abstract Optional<T> findEntityById(long id) throws DaoException;

    /**
     * Deletes entity
     *
     * @param entity the entity
     * @return the boolean
     */
    public abstract boolean delete(T entity);

    /**
     * Deletes entity
     *
     * @param id the entity id
     * @return the boolean
     */
    public abstract boolean delete(long id) throws DaoException;

    /**
     * Creates entity
     *
     * @param entity the entity
     * @return the int
     */
    public abstract int create(T entity) throws DaoException;

    /**
     * Updates entity
     *
     * @param entity the entity
     * @return the int
     */
    public abstract int update(T entity) throws DaoException;

    public void statementClose(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Statement isn't closed");
            }
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

