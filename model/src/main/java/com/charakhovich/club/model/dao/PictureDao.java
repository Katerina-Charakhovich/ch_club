package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Picture;
import com.charakhovich.club.model.exeption.DaoException;

import java.io.InputStream;
import java.util.List;

/**
 * The interface Picture dao.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface PictureDao {
    /**
     * Returns a list of pictures by event id,by picture type
     *
     * @param eventId the event id
     * @param type    the picture type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Picture> findPicturesByEvent(long eventId, Picture.Type type) throws DaoException;

    /**
     * Returns a list of pictures by event id,by picture type
     *
     * @param eventId   the event id
     * @param pictureId the picture id
     * @return the int
     * @throws DaoException the dao exception
     */
    int createByEventId(long eventId, long pictureId) throws DaoException;

    /**
     * Returns a list of pictures by event id,by picture type
     *
     * @param eventId the event id
     * @param photo   the photo
     * @param type    the picture type
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(long eventId, InputStream photo, Picture.Type type) throws DaoException;
}