package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.exeption.ServiceException;

import java.util.List;

public interface MessageEventService {
    List<MessageEvent> findMessagesEvent(long eventId) throws ServiceException;
    List<MessageEvent> findMessagesEvent(long eventId, Page page) throws ServiceException;
    int countOfMessages(long eventId) throws ServiceException;
    int create(MessageEvent entity) throws ServiceException;
    List<MessageEvent> findMessagesEvent(MessageEvent.State state, Page page) throws ServiceException ;
    int countOfMessageByState(MessageEvent.State state) throws ServiceException ;
    boolean updateState(long messageId, MessageEvent.State state)throws ServiceException ;
}
