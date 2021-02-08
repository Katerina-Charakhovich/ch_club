package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface event dates dao.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface EventDateDao {
    /**
     * Returns a list of schedules by event
     *
     * @param eventId the event id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<EventDate> findEventDates(long eventId) throws DaoException;

    /**
     * Returns a list of schedules by event limited to a page.
     *
     * @param eventId the event id
     * @param page    the page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<EventDate> findEventDates(long eventId, Page page) throws DaoException;

    /**
     * Returns a list of schedules by event by date
     *
     * @param eventId   the event id
     * @param localDate the date
     * @return the list
     * @throws DaoException the dao exception
     */
    List<EventDate> findEventDates(long eventId, LocalDate localDate) throws DaoException;

    /**
     * Returns count of schedules by event id
     *
     * @param eventId the event id
     * @return the int
     * @throws DaoException the dao exception
     */
    int count(long eventId) throws DaoException;

    /**
     * Returns count of dates by event id
     *
     * @param eventId the event id
     * @return the int
     * @throws DaoException the dao exception
     */
    int countDates(long eventId) throws DaoException;

    /**
     * Returns a list of dates by event limited to a page.
     *
     * @param eventId the event id
     * @param page    the page
     * @return the int
     * @throws DaoException the dao exception
     */
    List<LocalDate> findEventDatesQuest(long eventId, Page page) throws DaoException;
    /**
     * Returns a list of dates by event .
     *
     * @param eventId the event id
     * @return the int
     * @throws DaoException the dao exception
     */
    List<LocalDate> findEventDatesQuest(long eventId) throws DaoException;
}
