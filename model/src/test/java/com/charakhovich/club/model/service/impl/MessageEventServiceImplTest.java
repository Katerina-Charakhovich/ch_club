package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.impl.EventDaoImpl;
import com.charakhovich.club.model.dao.impl.MessageEventDaoImpl;
import com.charakhovich.club.model.dao.impl.UserDaoImpl;
import com.charakhovich.club.model.entity.MessageEvent;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class MessageEventServiceImplTest {
    MessageEventServiceImpl messageEventService;
    MessageEventDaoImpl messageEventDao;
    UserDaoImpl userDao;
    EventDaoImpl eventDao;
    List<MessageEvent> expected;


    @BeforeMethod
    public void setUp() {
        messageEventDao = Mockito.mock(MessageEventDaoImpl.class);
        eventDao = new EventDaoImpl();
        userDao = new UserDaoImpl();
        messageEventService = new MessageEventServiceImpl();
        WhiteboxImpl.setInternalState(messageEventService, "messageEventDao", messageEventDao);
        WhiteboxImpl.setInternalState(messageEventService, "eventDao", eventDao);
        WhiteboxImpl.setInternalState(messageEventService, "userDao", userDao);
        expected = new ArrayList<>();
    }

    @Test
    public void testFindMessagesEvent() {
        long eventId = 1;
        try {
            when(messageEventDao.findMessagesEvent(eventId)).thenReturn(expected);
            List<MessageEvent> actual = messageEventService.findMessagesEvent(eventId);
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail("Incorrect data", e);
        }
    }


    @Test
    public void testCountOfMessages() {
        long eventId=1;
        int expected = 1;
        try {
            when(messageEventDao.countOfMessages(eventId)).thenReturn(expected);
            assertEquals(expected, messageEventService.countOfMessages(eventId));
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCountOfMessageByState() {
        MessageEvent.State state = MessageEvent.State.ACTUAL;
        int expected = 1;
        try {
            when(messageEventDao.countOfMessageByState(state)).thenReturn(expected);
            assertEquals(expected, messageEventService.countOfMessageByState(state));
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateState() {
        long messageId = 1;
        MessageEvent.State state = MessageEvent.State.ACTUAL;
        try {
            when(messageEventDao.updateState(1, state)).thenReturn(true);
            assertTrue( messageEventService.updateState(1, state));
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }
}
