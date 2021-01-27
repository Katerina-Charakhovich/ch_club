package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.EntityTransaction;
import com.charakhovich.club.model.dao.EventDateDao;
import com.charakhovich.club.model.dao.impl.EventDaoImpl;
import com.charakhovich.club.model.dao.impl.EventDateDaoImpl;
import com.charakhovich.club.model.dao.impl.TicketDaoImpl;
import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.EventDate;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventDateService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class EventDateServiceImpl implements EventDateService {

    @Override
    public List<EventDate> findEventDates(long eventId) throws ServiceException {
        EventDateDao eventDateDao = new EventDateDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init((AbstractDao) eventDateDao);
        List<EventDate> listEventDate;
        try {
            listEventDate = eventDateDao.findEventDates(eventId);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return listEventDate;
    }

    @Override
    public List<EventDate> findEventDates(long eventId, Page page) throws ServiceException {
        EventDateDao eventDateDao = new EventDateDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init((AbstractDao) eventDateDao);
        List<EventDate> listEventDate;
        try {
            listEventDate = eventDateDao.findEventDates(eventId, page);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return listEventDate;
    }

    @Override
    public List<EventDate> findEventDates(long eventId, LocalDate localDate) throws ServiceException {
        EventDateDao eventDateDao = new EventDateDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init((AbstractDao) eventDateDao);
        List<EventDate> listEventDate;
        try {
            listEventDate = eventDateDao.findEventDates(eventId, localDate);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return listEventDate;
    }

    @Override
    public List<LocalDate> findEventDatesQuest(long eventId, Page page) throws ServiceException {
        EventDateDao eventDateDao = new EventDateDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init((AbstractDao) eventDateDao);
        List<LocalDate> listDates;
        try {
            listDates = eventDateDao.findEventDatesQuest(eventId, page);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return listDates;
    }

    @Override
    public int count(long eventId) throws ServiceException {
        EventDateDao eventDateDao = new EventDateDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init((AbstractDao) eventDateDao);
        int result = 0;
        try {
            result = eventDateDao.count(eventId);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public int countDates(long eventId) throws ServiceException {
        EventDateDao eventDateDao = new EventDateDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init((AbstractDao) eventDateDao);
        int result = 0;
        try {
            result = eventDateDao.countDates(eventId);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public int create(EventDate entity) throws ServiceException {
        EventDateDaoImpl eventDateDao = new EventDateDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(eventDateDao);
        int result = -1;
        try {
            result = eventDateDao.create(entity);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public int createQuestSchedule(long eventId,
                                   LocalDate localDate,
                                   LocalTime starTime,
                                   LocalTime endTime,
                                   BigDecimal cost) throws ServiceException {
        boolean flag = true;
        int result = -1;
        EventDateDaoImpl eventDateDao = new EventDateDaoImpl();
        EventDaoImpl eventDao = new EventDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(eventDateDao, eventDao);
        try {
            Optional<Event> eventOptional = eventDao.findEntityById(eventId);
            int i = 0;
            while (flag) {
                LocalDateTime localDateTime = localDate.atTime(starTime.plusMinutes((long) (eventOptional.get().getDuration() * 60 * i)));
                EventDate eventDate = new EventDate(eventId, localDateTime, cost, 1);
                result = eventDateDao.create(eventDate);
                LocalDateTime newTime=localDate.atTime(starTime.plusMinutes((long) (eventOptional.get().getDuration() * 60 * (i+1))));
                flag = newTime.toLocalTime().compareTo(endTime)> 0 ? false : true;
                i++;
            }
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public int registryTicket(Ticket ticket) throws ServiceException {
        int result = -1;
        EventDateDaoImpl eventDateDao = new EventDateDaoImpl();
        TicketDaoImpl ticketDao = new TicketDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(eventDateDao, ticketDao);
        try {
            Optional<EventDate> eventDate = eventDateDao.findEntityById(ticket.getScheduleId());
            if (eventDate.get().getFreeTicketCount() >= ticket.getCountTicket()) {
                result = ticketDao.create(ticket);
            }
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result;
    }
}
