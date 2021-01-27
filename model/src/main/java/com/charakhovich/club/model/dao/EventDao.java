package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.DaoException;

import java.util.List;

public interface EventDao {
    List<Event> findAll(Page page) throws DaoException;
    List<Event> findAll(Event.Type type, Page page) throws DaoException;
    int count() throws DaoException;
    int count(Event.Type type) throws DaoException;
}
