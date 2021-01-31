package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The transaction with DB
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class EntityTransaction {
    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Logger logger = LogManager.getLogger(EntityTransaction.class);

    /**
     * Gets connection for transaction
     * sets DB auto commit - to false
     *
     * @param dao  App Dao
     * @param daos one more App Dao
     * @throws DaoException the dao exception
     */
    public void initTransaction(AbstractDao dao, AbstractDao... daos) throws DaoException {
        if (connection == null) {
            try {
                connection = connectionPool.getConnection();
                connection.setAutoCommit(false);
                dao.setConnection(connection);
                for (AbstractDao daoElement : daos) {
                    daoElement.setConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("Error during transaction init", e);
            }
        }
    }

    /**
     * Ends transaction
     * sets DB commit back to auto
     * takes back connection
     *
     * @throws DaoException the dao exception
     */
    public void endTransaction() {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Can not return autocommit to connection");
            }
            connection = null;
        }
    }

    /**
     * Gets connection for single query or multi-select queries
     *
     * @param dao  App Dao
     * @param daos one more App Dao
     */
    public void initSingleQuery(AbstractDao dao, AbstractDao... daos) {
        if (connection == null) {
            connection = connectionPool.getConnection();
        }
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    /**
     * Returns connection for single query or multi-select queries
     */
    public void endSingleQuery() {
        if (connection != null) {
            connectionPool.returnConnection(connection);
            connection = null;
        }
    }

    /**
     * Commits transaction
     *
     * @throws DaoException the dao exception
     */
    public void commit() throws DaoException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new DaoException("Commit error", e);
            }
        }
    }

    /**
     * Rollbacks changes
     *
     * @throws DaoException - general Exception of Dao layer
     */
    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Error during rollback connection", e);
        }
    }
}