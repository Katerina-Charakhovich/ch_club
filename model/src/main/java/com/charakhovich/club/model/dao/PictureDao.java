package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.Picture;
import com.charakhovich.club.model.exeption.DaoException;

import java.io.InputStream;
import java.util.List;

public interface PictureDao {

    List<Picture> findPicturesByEvent(long eventId, Picture.Type type) throws DaoException;
    int createByEventId(long eventId, long pictureId) throws DaoException;
    int create(long eventId, InputStream photo, Picture.Type type) throws DaoException;
}