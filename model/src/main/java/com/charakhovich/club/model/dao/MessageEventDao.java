package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.DaoException;

import java.util.List;

/**
 * The interface message event dao.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface MessageEventDao {
    /**
     * Returns a list of messages by event id
     *
     * @param eventId the event id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<MessageEvent> findMessagesEvent(long eventId) throws DaoException;

    /**
     * Returns a list of messages by event id, by page
     *
     * @param eventId the event id
     * @param page    the page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<MessageEvent> findMessagesEvent(long eventId, Page page) throws DaoException;

    /**
     * Returns a list of messages by message state, by page
     *
     * @param state the message state
     * @param page  the page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<MessageEvent> findMessagesEvent(MessageEvent.State state, Page page) throws DaoException;

    /**
     * Returns a count of messages by event id
     *
     * @param eventId the event id
     * @return the int
     * @throws DaoException the dao exception
     */
    int countOfMessages(long eventId) throws DaoException;

    /**
     * Returns a count of messages by event state
     *
     * @param state the message state
     * @return the int
     * @throws DaoException the dao exception
     */
    int countOfMessageByState(MessageEvent.State state) throws DaoException;

    /**
     * Updates state message
     *
     * @param messageId the message id
     * @param state     the message state
     * @return the int
     * @throws DaoException the dao exception
     */
    boolean updateState(long messageId, MessageEvent.State state) throws DaoException;
}
