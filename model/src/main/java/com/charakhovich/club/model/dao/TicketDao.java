package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.exeption.DaoException;

import java.util.List;

public interface TicketDao {
    List<Ticket> findTicketEvent(long eventId) throws DaoException;
    List<EventDate> findTicketEvent(long eventId, Page page) throws DaoException;
    int count(long eventId) throws DaoException;
}
