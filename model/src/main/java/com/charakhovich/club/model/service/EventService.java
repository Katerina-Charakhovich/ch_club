package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll(Page page) throws ServiceException;
    List<Event> findAll(Event.Type type,Page page) throws ServiceException;
    boolean create(Event entity) throws ServiceException;
    Optional<Event> findEntityById(long id) throws ServiceException;
    int count() throws ServiceException;
    boolean create(Event event,InputStream mainPicture, List<InputStream> listPictures)  throws ServiceException;
    int count(Event.Type type) throws ServiceException;
    boolean update(Event event) throws ServiceException;
}
