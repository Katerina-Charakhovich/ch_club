package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Logger logger = LogManager.getLogger(EntityTransaction.class);

    public void initTransaction(AbstractDao dao, AbstractDao... daos) {
        if (connection == null) {
            connection = connectionPool.getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    public void endTransaction() {
     //  if (connection == null)
            try {
                // connection check code for commit
                connection.setAutoCommit(true);
            } catch (SQLException e) {
               logger.log(Level.ERROR,"Can not return autocommit to connection");
            }
        connectionPool.returnConnection(connection);
        connection = null;
    }

    public void init(AbstractDao dao, AbstractDao... daos) {
        if (connection == null) {
            connection = connectionPool.getConnection();
        }
        dao.setConnection(connection);
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    public void end() {

        connectionPool.returnConnection(connection);
        connection = null;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Can not commit transaction");
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Can not rollback transaction");
        }
    }
}