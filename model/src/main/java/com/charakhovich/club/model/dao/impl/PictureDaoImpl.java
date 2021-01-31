package com.charakhovich.club.model.dao.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.PictureDao;
import com.charakhovich.club.model.dao.SqlQuery;
import com.charakhovich.club.model.dao.TableColumnName;
import com.charakhovich.club.model.entity.*;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.util.PictureUtil;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PictureDaoImpl extends AbstractDao<Picture> implements PictureDao {

    @Override
    public List<Picture> findPicturesByEvent(long eventId, Picture.Type type) throws DaoException {
        List<Picture> listPictures = new ArrayList<Picture>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_PICTURE_BY_EVENT_BY_TYPE);
            statement.setLong(1, eventId);
            statement.setString(2, Picture.State.ACTUAL.toString());
            statement.setString(3, type.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Picture picture = new Picture();
                picture.setPictureId(resultSet.getLong(TableColumnName.PICTURE_DAO_ID));
                picture.setType(Picture.Type.valueOf(resultSet.getString(TableColumnName.PICTURE_DAO_TYPE)
                        .toUpperCase()));
                InputStream inputStream = resultSet.getBinaryStream(TableColumnName.PICTURE_DAO_BLOB);
                String blob = PictureUtil.imageToBase64(inputStream);
                picture.setName(blob);
                listPictures.add(picture);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return listPictures;

    }

    @Override
    public List findAll()  {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Picture> findEntityById(long id)  {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Picture entity) {

        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {

        throw new UnsupportedOperationException();
    }

    @Override
    public int create(Picture entity) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_PICTURE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getType().toString());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(4, Picture.State.ACTUAL.toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            int key = -1;
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
    }

    @Override
    public int createByEventId(long eventId, long pictureId) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_PICTURE_BY_EVENT_ID, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, pictureId);
            statement.setLong(2, eventId);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(4, Picture.State.ACTUAL.toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            int key = -1;
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
    }

    @Override
    public int create(long eventId, InputStream photo, Picture.Type type) throws DaoException {

        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_PICTURE_BLOB, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, eventId);
            statement.setString(2, type.toString());
            statement.setBlob(3, photo);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            int key = -1;
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
    }

    @Override
    public int update(Picture entity) {
        throw new UnsupportedOperationException();
    }
}
