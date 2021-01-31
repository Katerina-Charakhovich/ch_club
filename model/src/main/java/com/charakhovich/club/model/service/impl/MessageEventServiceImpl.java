package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.EntityTransaction;
import com.charakhovich.club.model.dao.impl.EventDaoImpl;
import com.charakhovich.club.model.dao.impl.MessageEventDaoImpl;
import com.charakhovich.club.model.dao.impl.PictureDaoImpl;
import com.charakhovich.club.model.dao.impl.UserDaoImpl;
import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.MessageEventService;

import java.util.List;
import java.util.Optional;

public class MessageEventServiceImpl implements MessageEventService {
    private MessageEventDaoImpl messageEventDao;
    private UserDaoImpl userDao;
    private EventDaoImpl eventDao;

    public MessageEventServiceImpl() {
        messageEventDao = new MessageEventDaoImpl();
        eventDao = new EventDaoImpl();
        userDao = new UserDaoImpl();
    }

    @Override
    public List<MessageEvent> findMessagesEvent(long eventId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        List<MessageEvent> listMessageEvent;
        try {
            transaction.initSingleQuery(messageEventDao);
            listMessageEvent = messageEventDao.findMessagesEvent(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listMessageEvent;
    }

    @Override
    public List<MessageEvent> findMessagesEvent(long eventId, Page page) throws ServiceException {
        MessageEventDaoImpl messageEventDao = new MessageEventDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(messageEventDao, userDao, eventDao);
        List<MessageEvent> listMessageEvent;
        try {
            listMessageEvent = messageEventDao.findMessagesEvent(eventId, page);
            for (MessageEvent message : listMessageEvent
            ) {
                Optional<User> user = userDao.findEntityById(message.getUser().getUserId());
                message.setUser(user.get());
            }
            for (MessageEvent message : listMessageEvent
            ) {
                Optional<Event> event = eventDao.findEntityById(message.getEvent().getEventId());
                message.setEvent(event.get());
            }

        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listMessageEvent;
    }

    @Override
    public int countOfMessages(long eventId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(messageEventDao);
        int result = 0;
        try {
            result = messageEventDao.countOfMessages(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }

    @Override
    public int create(MessageEvent entity) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(messageEventDao);
        int result = -1;
        try {
            result = messageEventDao.create(entity);
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                daoException.printStackTrace();
            }
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }

    @Override
    public List<MessageEvent> findMessagesEvent(MessageEvent.State state, Page page) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(messageEventDao, userDao, eventDao);
        List<MessageEvent> listMessageEvent;
        try {
            listMessageEvent = messageEventDao.findMessagesEvent(state, page);
            for (MessageEvent message : listMessageEvent
            ) {
                Optional<User> user = userDao.findEntityById(message.getUser().getUserId());
                message.setUser(user.get());
            }
            for (MessageEvent message : listMessageEvent
            ) {
                Optional<Event> event = eventDao.findEntityById(message.getEvent().getEventId());
                message.setEvent(event.get());
            }

        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listMessageEvent;
    }

    @Override
    public int countOfMessageByState(MessageEvent.State state) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(messageEventDao);
        int result = 0;
        try {
            result = messageEventDao.countOfMessageByState(state);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }

    @Override
    public boolean updateState(long messageId, MessageEvent.State state) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(messageEventDao);
        boolean result = false;
        try {
            result = messageEventDao.updateState(messageId, state);
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                daoException.printStackTrace();
            }
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }
}
