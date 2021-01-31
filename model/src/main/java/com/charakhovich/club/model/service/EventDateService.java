package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.exeption.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The interface event dates service.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface EventDateService {
    /**
     * Returns a list of schedules by event
     *
     * @param eventId the event id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<EventDate> findEventDates(long eventId) throws ServiceException;

    /**
     * Returns a list of schedules by event limited to a page.
     *
     * @param eventId the event id
     * @param page    the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<EventDate> findEventDates(long eventId, Page page) throws ServiceException;

    /**
     * Returns a list of dates by event, limited to a page later than the current date
     *
     * @param eventId the event id
     * @param page    the page
     * @return the int
     * @throws ServiceException the service exception
     */
    List<EventDate> findEventAvailableDates(long eventId, Page page) throws ServiceException;

    /**
     * Returns a list of schedules by event by date
     *
     * @param eventId   the event id
     * @param localDate the date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<EventDate> findEventDates(long eventId, LocalDate localDate) throws ServiceException;

    /**
     * Returns a list of dates by event limited to a page.
     *
     * @param eventId the event id
     * @param page    the page
     * @return the int
     * @throws ServiceException the service exception
     */
    List<LocalDate> findEventDatesQuest(long eventId, Page page) throws ServiceException;

    /**
     * Returns a list of dates by quest, limited to a page later than the current date
     *
     * @param eventId the event id
     * @param page    the page
     * @return the int
     * @throws ServiceException the service exception
     */
    List<LocalDate> findAvailableEventDatesQuest(long eventId, Page page) throws ServiceException;

    /**
     * Returns count of schedules by event id
     *
     * @param eventId the event id
     * @return the int
     * @throws ServiceException the service exception
     */
    int count(long eventId) throws ServiceException;

    /**
     * Returns count of dates by event id
     *
     * @param eventId the event id
     * @return the int
     * @throws ServiceException the service exception
     */
    int countDates(long eventId) throws ServiceException;

    /**
     * Creates event with pictures.
     *
     * @param eventDate the event date
     * @return the int
     * @throws ServiceException the service exception
     */
    int create(EventDate eventDate) throws ServiceException;

    /**
     * Creates quest schedule for a specific date
     *
     * @param eventId   the event id
     * @param localDate the date for schedule creation
     * @param startTime the start time for schedule creation
     * @param endTime   the end time for schedule creation
     * @param price     the price of quest
     * @return the int
     * @throws ServiceException the service exception
     */
    int createQuestSchedule(long eventId, LocalDate localDate, LocalTime startTime, LocalTime endTime, BigDecimal price)
            throws ServiceException;

    /**
     * Registries a ticket for the event
     *
     * @param ticket the ticket
     * @param price  the ticket price
     * @return the int
     * @throws ServiceException the service exception
     */
    int registryTicket(Ticket ticket, BigDecimal price) throws ServiceException;
}
