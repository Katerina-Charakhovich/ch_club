package com.charakhovich.club.model.service.impl;


import com.charakhovich.club.model.dao.impl.EventDaoImpl;
import com.charakhovich.club.model.dao.impl.PictureDaoImpl;
import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.Picture;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class EventServiceImplTest {
    EventServiceImpl eventService;
    EventDaoImpl eventDao;
    PictureDaoImpl pictureDao;
    List<Event> expectedListEvent;
    List<Picture> expectedListPicture;
    Event expectedEvent;

    @BeforeMethod
    public void setUp() {
        eventDao = Mockito.mock(EventDaoImpl.class);
        pictureDao = Mockito.mock(PictureDaoImpl.class);
        eventService = new EventServiceImpl();
        WhiteboxImpl.setInternalState(eventService, "eventDao", eventDao);
        expectedListEvent = new ArrayList<>();
        Event expectedEvent=new Event(1, Event.Type.QUEST,"TestName","TestDescription",Event.State.ACTUAL,"TestShrtDescription",0.5);
        expectedListEvent.add(expectedEvent);
        expectedListPicture=new ArrayList<>();
    }
    @Test
    public void testFindAll() {
        try {
            Page page=new Page(1,5);
            when(eventDao.findAll(page)).thenReturn(expectedListEvent);
            when(pictureDao.findPicturesByEvent(1, Picture.Type.MAIN)).thenReturn(expectedListPicture);
            List<Event> actual = eventService.findAll(page);
            assertEquals(actual, expectedListEvent);
        } catch (ServiceException | DaoException e) {
            fail("Incorrect data", e);
        }
    }

    @Test
    public void testTestFindAll() {
        try {
            Page page=new Page(1,5);
            when(eventDao.findAll(Event.Type.THEATRE,page)).thenReturn(expectedListEvent);
            when(pictureDao.findPicturesByEvent(1, Picture.Type.MAIN)).thenReturn(expectedListPicture);
            List<Event> actual = eventService.findAll(Event.Type.THEATRE,page);
            assertEquals(actual, expectedListEvent);
        } catch (ServiceException | DaoException e) {
            fail("Incorrect data", e);
        }
    }



    @Test
    public void testCount() {
        int expectedCount = 1;
        try {
            when(eventDao.count()).thenReturn(expectedCount);
            assertEquals(expectedCount, eventService.count());
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTestCount() {
        int expected = 1;
        try {
            when(eventDao.count(Event.Type.QUEST)).thenReturn(expected);
            assertEquals(expected, eventService.count(Event.Type.QUEST));
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        boolean expected = true;
        try {
            when(eventDao.update(expectedEvent)).thenReturn(1);
            assertEquals(expected, eventService.update(expectedEvent));
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }



}