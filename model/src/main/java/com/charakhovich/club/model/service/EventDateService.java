package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.exeption.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventDateService {
    List<EventDate> findEventDates(long eventId) throws ServiceException;
    List<EventDate> findEventDates(long eventId, Page page) throws ServiceException;
    List<EventDate> findEventDates(long eventId, LocalDate localDate) throws ServiceException;
    List<LocalDate> findEventDatesQuest(long eventId, Page page) throws ServiceException;
    int count(long eventId) throws ServiceException;
    int countDates(long eventId) throws ServiceException;
    int create(EventDate entity) throws ServiceException;
    int createQuestSchedule (long eventId, LocalDate localDate, LocalTime starTime, LocalTime endTime, BigDecimal cost)
            throws ServiceException;
    int registryTicket(Ticket ticket) throws ServiceException;
}
