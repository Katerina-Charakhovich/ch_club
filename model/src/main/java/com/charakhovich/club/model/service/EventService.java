package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * The interface Event service.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface EventService {
    /**
     * Returns a list of events limited to a page
     *
     * @param page the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Event> findAll(Page page) throws ServiceException;

    /**
     * Returns a list of events by event type limited to a page.
     *
     * @param page the page
     * @param type the event type
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Event> findAll(Event.Type type, Page page) throws ServiceException;

    /**
     * Creates event.
     *
     * @param event the event.
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(Event event) throws ServiceException, DaoException;

    /**
     * Finds event by event id.
     *
     * @param eventId the event id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Event> findEntityById(long eventId) throws ServiceException;

    /**
     * Returns count of events
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int count() throws ServiceException;

    /**
     * Creates event with pictures.
     *
     * @param event        the event
     * @param mainPicture  the main picture
     * @param listPictures the list list of additional pictures
     * @return the long
     * @throws ServiceException the service exception
     */
    long create(Event event, InputStream mainPicture, List<InputStream> listPictures) throws ServiceException;

    /**
     * Returns count of events by event type
     *
     * @param type the event type
     * @return the int
     * @throws ServiceException the service exception
     */
    int count(Event.Type type) throws ServiceException;

    /**
     * Updates event info.
     *
     * @param event the event
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(Event event) throws ServiceException;
}
