package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.EntityTransaction;
import com.charakhovich.club.model.dao.impl.EventDaoImpl;
import com.charakhovich.club.model.dao.impl.PictureDaoImpl;
import com.charakhovich.club.model.entity.Event;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.Picture;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.EventService;
import com.charakhovich.club.model.util.PictureUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService {
    private static final int IMAGE_WIDTH_MAIN = 300;
    private static final int IMAGE_WIDTH_ADDITIONAL = 600;

    @Override
    public List<Event> findAll(Page page) throws ServiceException {
        EventDaoImpl eventDao = new EventDaoImpl();
        PictureDaoImpl pictureDao = new PictureDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(eventDao, pictureDao);
        List<Event> result;
        try {
            result = eventDao.findAll(page);
            for (Event event : result
            ) {
                List<Picture> listPicture = pictureDao.findPicturesByEvent(event.getEventId(), Picture.Type.MAIN);
                if (listPicture != null && listPicture.size() > 0)

                    event.setMainPicture(listPicture.get(0));

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
    public List<Event> findAll(Event.Type type, Page page) throws ServiceException {
        EventDaoImpl eventDao = new EventDaoImpl();
        PictureDaoImpl pictureDao = new PictureDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(eventDao, pictureDao);
        List<Event> result;
        try {
            result = eventDao.findAll(type, page);
            for (Event event : result
            ) {
                List<Picture> listPicture = pictureDao.findPicturesByEvent(event.getEventId(), Picture.Type.MAIN);
                if (listPicture != null && listPicture.size() > 0)

                    event.setMainPicture(listPicture.get(0));

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
    public boolean create(Event entity) throws ServiceException {
        EventDaoImpl eventDao = new EventDaoImpl();
        PictureDaoImpl pictureDao = new PictureDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(eventDao, pictureDao);
        try {
            long eventId = eventDao.create(entity);
            entity.setEventId(eventId);
            List<Long> listPictureId = new ArrayList<>();
            long pictureMainId = pictureDao.create(entity.getMainPicture());
            listPictureId.add(pictureMainId);
            for (Picture picture : entity.getListAdditionalPicture()
            ) {
                long pictureAdditionalId = pictureDao.create(picture);
                listPictureId.add(pictureAdditionalId);
            }
            for (Long pictureId : listPictureId
            ) {
                pictureDao.createByEventId(eventId, pictureId);
            }

            return true;
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }

    @Override
    public Optional<Event> findEntityById(long id) throws ServiceException {
        EventDaoImpl eventDao = new EventDaoImpl();
        PictureDaoImpl pictureDao = new PictureDaoImpl();
        EntityTransaction transactionEventDao = new EntityTransaction();
        EntityTransaction transactionPictureDao = new EntityTransaction();
        transactionEventDao.init(eventDao);
        transactionPictureDao.init(pictureDao);
        try {
            Optional<Event> result = eventDao.findEntityById(id);
            if (result.isPresent()) {
                Event event = result.get();
                List<Picture> listPicture = pictureDao.findPicturesByEvent(id, Picture.Type.ADDITIONAL);
                event.setListAdditionalPicture(listPicture);
                result = Optional.of(event);
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transactionEventDao.end();
            transactionPictureDao.end();
        }
    }

    @Override
    public int count() throws ServiceException {
        EventDaoImpl eventDao = new EventDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(eventDao);
        int result = 0;
        try {
            result = eventDao.count();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public int count(Event.Type type) throws ServiceException {
        EventDaoImpl eventDao = new EventDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(eventDao);
        int result = 0;
        try {
            result = eventDao.count(type);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public boolean update(Event event) throws ServiceException {
        int result=-1;
        EventDaoImpl eventDao = new EventDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(eventDao);
        try {
            result=eventDao.update(event);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result>0?true:false;
    }


    @Override
    public boolean create(Event event, InputStream mainPicture, List<InputStream> listPictures) throws ServiceException {
        EventDaoImpl eventDao = new EventDaoImpl();
        PictureDaoImpl pictureDao = new PictureDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(eventDao, pictureDao);
        try {
            long eventId = eventDao.create(event);
            event.setEventId(eventId);
            InputStream newPicture = PictureUtil.resizeImage(mainPicture, IMAGE_WIDTH_MAIN);
            long pictureMainId = pictureDao.create(event.getEventId(), newPicture, Picture.Type.MAIN);
            for (InputStream picture : listPictures
            ) {
                InputStream newAddPicture = PictureUtil.resizeImage(picture, IMAGE_WIDTH_ADDITIONAL);
                long pictureAdditionalId = pictureDao.create(event.getEventId(), newAddPicture, Picture.Type.ADDITIONAL);
            }

            return true;
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }

}
