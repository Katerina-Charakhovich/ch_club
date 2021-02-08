package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.EntityTransaction;
import com.charakhovich.club.model.dao.impl.*;
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
import java.util.stream.Collectors;

public class EventDateServiceImpl implements EventDateService {
    private EventDaoImpl eventDao;
    private UserDaoImpl userDao;
    private EventDateDaoImpl eventDateDao;
    private TicketDaoImpl ticketDao;

    public EventDateServiceImpl() {
        eventDateDao = new EventDateDaoImpl();
        eventDao = new EventDaoImpl();
        userDao = new UserDaoImpl();
        ticketDao = new TicketDaoImpl();
    }

    @Override
    public List<EventDate> findEventDates(long eventId) throws ServiceException {
        List<EventDate> listEventDate;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initSingleQuery(eventDateDao);
            listEventDate = eventDateDao.findEventDates(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listEventDate;
    }

    @Override
    public List<EventDate> findEventDates(long eventId, Page page) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        List<EventDate> listEventDate;
        try {
            transaction.initSingleQuery(eventDateDao);
            listEventDate = eventDateDao.findEventDates(eventId, page);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listEventDate;
    }

    @Override
    public List<EventDate> findEventAvailableDates(long eventId, Page page) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();

        List<EventDate> listEventDate;
        try {
            transaction.initSingleQuery(eventDateDao);
            List<EventDate> tempListEventDate = eventDateDao.findEventDates(eventId, page);
            listEventDate = tempListEventDate.stream().filter(s -> (s.getEventDateTime().compareTo(LocalDateTime.now()) > -1))
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listEventDate;
    }

    @Override
    public List<EventDate> findEventDates(long eventId, LocalDate localDate) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(eventDateDao);
        List<EventDate> listEventDate;
        try {
            listEventDate = eventDateDao.findEventDates(eventId, localDate);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listEventDate;
    }

    @Override
    public List<LocalDate> findEventDatesQuest(long eventId, Page page) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(eventDateDao);
        List<LocalDate> listDates;
        try {
            listDates = eventDateDao.findEventDatesQuest(eventId, page);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listDates;
    }

    @Override
    public List<LocalDate> findAvailableEventDatesQuest(long eventId, Page page) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(eventDateDao);
        List<LocalDate> listDates;
        try {
            List<LocalDate> tempListEventDate = eventDateDao.findEventDatesQuest(eventId, page);
            listDates = tempListEventDate.stream().filter(s -> s.compareTo(LocalDate.now()) > -1).collect(Collectors.toList());

        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return listDates;
    }

    @Override
    public int count(long eventId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(eventDateDao);
        int result = 0;
        try {
            result = eventDateDao.count(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }

    @Override
    public int countDates(long eventId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(eventDateDao);
        int result = 0;
        try {
            result = eventDateDao.countDates(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }

    @Override
    public int create(EventDate entity) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        int result = -1;
        try {
            transaction.initSingleQuery(eventDateDao);
            result = eventDateDao.create(entity);
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
    public int createQuestSchedule(long eventId,
                                   LocalDate localDate,
                                   LocalTime starTime,
                                   LocalTime endTime,
                                   BigDecimal cost) throws ServiceException {
        boolean flag = true;
        int result = -1;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initTransaction(eventDateDao, eventDao);
            Optional<Event> eventOptional = eventDao.findEntityById(eventId);
            List<LocalDate> listdates = eventDateDao.findEventDatesQuest(eventId);
            long count = listdates.stream().filter(s -> s.equals(localDate)).count();
            if (count < 1) {
                int i = 0;
                while (flag) {
                    LocalDateTime localDateTime = localDate.atTime(starTime.plusMinutes((long) (eventOptional.get().getDuration() * 60 * i)));
                    EventDate eventDate = new EventDate(eventId, localDateTime, cost, 1);
                    result = eventDateDao.create(eventDate);
                    LocalDateTime newTime = localDate.atTime(starTime.plusMinutes((long) (eventOptional.get().getDuration() * 60 * (i + 1))));
                    flag = newTime.toLocalTime().compareTo(endTime) > 0 ? false : true;
                    i++;
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                daoException.printStackTrace();
            }
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result;
    }

    @Override
    public int registryTicket(Ticket ticket, BigDecimal price) throws ServiceException {
        int result = -1;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initTransaction(eventDateDao, ticketDao, userDao);
            Optional<EventDate> eventDate = eventDateDao.findEntityById(ticket.getEventDateId());
            if (eventDate.get().getFreeTicketCount() >= ticket.getCountTicket()) {
                result = ticketDao.create(ticket);
            }
            boolean resultUpdateBalance = userDao.minusBalance(ticket.getUserId(), BigDecimal.valueOf(ticket.getCountTicket() * price.doubleValue()));
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                daoException.printStackTrace();
            }
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result;
    }
}
