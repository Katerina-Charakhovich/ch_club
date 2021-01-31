package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.exeption.DaoException;

import java.util.List;

/**
 * The interface Ticket dao.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface TicketDao {
    /**
     * Returns a list of tickets by event
     *
     * @param eventId the event id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Ticket> findTicketEvent(long eventId) throws DaoException;

    /**
     * Returns a list of event dates by event
     *
     * @param eventId the event id
     * @param page    the  page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<EventDate> findTicketEvent(long eventId, Page page) throws DaoException;

    /**
     * Returns a list of tickets by event
     *
     * @param eventId the event id
     * @param page    the  page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Ticket> findTicketUser(long eventId, Page page) throws DaoException;

    /**
     * Returns a count of tickets by event id
     *
     * @param eventId the event id
     * @return the int
     * @throws DaoException the dao exception
     */
    int count(long eventId) throws DaoException;
}
