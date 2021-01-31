package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;

import java.util.List;

/**
 * The interface MessageEvent service.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface MessageEventService {
    /**
     * Returns a list of messages by event id
     *
     * @param eventId the event id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<MessageEvent> findMessagesEvent(long eventId) throws ServiceException;

    /**
     * Returns a list of messages by event id, by page
     *
     * @param eventId the event id
     * @param page    the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<MessageEvent> findMessagesEvent(long eventId, Page page) throws ServiceException;

    /**
     * Returns a count of messages by event id
     *
     * @param eventId the event id
     * @return the int
     * @throws ServiceException the service exception
     */
    int countOfMessages(long eventId) throws ServiceException;

    /**
     * Registers user.
     *
     * @param message the message
     * @return the int
     * @throws ServiceException the service exception
     */
    int create(MessageEvent message) throws ServiceException;

    /**
     * Returns a list of messages by message state, by page
     *
     * @param state the message state
     * @param page  the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<MessageEvent> findMessagesEvent(MessageEvent.State state, Page page) throws ServiceException;

    /**
     * Returns a count of messages by event state
     *
     * @param state the message state
     * @return the int
     * @throws ServiceException the service exception
     */
    int countOfMessageByState(MessageEvent.State state) throws ServiceException;

    /**
     * Update state message
     *
     * @param messageId the message id
     * @param state     the message state
     * @return the int
     * @throws ServiceException the service exception
     */
    boolean updateState(long messageId, MessageEvent.State state) throws ServiceException;
}
