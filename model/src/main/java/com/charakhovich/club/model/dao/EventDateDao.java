package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;

import java.time.LocalDate;
import java.util.List;

public interface EventDateDao {
    List<EventDate> findEventDates(long eventId) throws DaoException;
    List<EventDate> findEventDates(long eventId, Page page) throws DaoException;
    List<EventDate> findEventDates(long eventId, LocalDate localDate) throws DaoException;
    int count(long eventId) throws DaoException;
    int countDates(long eventId) throws DaoException;
    List <LocalDate> findEventDatesQuest(long eventId,Page page) throws DaoException;
}
