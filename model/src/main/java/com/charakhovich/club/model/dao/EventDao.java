package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.DaoException;

import java.util.List;

/**
 * The interface event dao.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface EventDao {
    /**
     * Returns a list of events limited to a page
     *
     * @param page the page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Event> findAll(Page page) throws DaoException;

    /**
     * Returns a list of events by event type limited to a page.
     *
     * @param page the page
     * @param type the event type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Event> findAll(Event.Type type, Page page) throws DaoException;

    /**
     * Returns count of events
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int count() throws DaoException;

    /**
     * Returns count of events by event type
     *
     * @param type the event type
     * @return the int
     * @throws DaoException the dao exception
     */
    int count(Event.Type type) throws DaoException;
}
