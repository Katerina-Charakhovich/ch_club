package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.DaoException;

import java.util.List;

public interface MessageEventDao {
    List<MessageEvent> findMessagesEvent(long eventId) throws DaoException;
    List<MessageEvent> findMessagesEvent(long eventId, Page page) throws DaoException;
    List<MessageEvent> findMessagesEvent(MessageEvent.State state,Page page)throws DaoException;
    int countOfMessages(long eventId) throws DaoException;
    int countOfMessageByState(MessageEvent.State state)throws DaoException;
    boolean updateState(long messageId,MessageEvent.State state) throws DaoException;
}
