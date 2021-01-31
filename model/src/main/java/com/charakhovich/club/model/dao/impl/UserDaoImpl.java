package com.charakhovich.club.model.dao.impl;

import com.charakhovich.club.model.dao.AbstractDao;
import com.charakhovich.club.model.dao.SqlQuery;
import com.charakhovich.club.model.dao.TableColumnName;
import com.charakhovich.club.model.dao.UserDao;
import com.charakhovich.club.model.entity.*;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.util.PictureUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong(TableColumnName.USER_DAO_ID));
                user.setLogin(resultSet.getString(TableColumnName.USER_DAO_LOGIN));
                user.setFirstName(resultSet.getString(TableColumnName.USER_DAO_FIRSTNAME));
                user.setLastName(resultSet.getString(TableColumnName.USER_DAO_LASTNAME));
                user.setRole(User.Role.valueOf(resultSet.getString(TableColumnName.USER_DAO_ROLE)));
                user.setState(User.State.valueOf(resultSet.getString(TableColumnName.USER_DAO_STATE)));
                user.setPhone(resultSet.getString(TableColumnName.USER_DAO_PHONE));
                user.setBalance(resultSet.getBigDecimal(TableColumnName.USER_DAO_BALANCE));
                if (resultSet.getBinaryStream(TableColumnName.USER_DAO_PHOTO) != null) {
                    InputStream inputStream = resultSet.getBinaryStream(TableColumnName.USER_DAO_PHOTO);
                    String photo = PictureUtil.imageToBase64(inputStream);
                    user.setPhoto(photo);
                }
                userOptional = Optional.of(user);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return userOptional;
    }

    @Override
    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        Optional<String> tempPassword = Optional.empty();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_PASSWORD_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString(TableColumnName.USER_DAO_PASSWORD);
                tempPassword = Optional.of(password);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return tempPassword;
    }

    @Override
    public int count(User.Role role) throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_USERS_BY_ROLE);
            statement.setString(1, role.toString());
            statement.setString(2, User.State.ACTUAL.toString());
            statement.setString(3, User.State.BLOCKED.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_USERS);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }

    @Override
    public List<User> findByRole(User.Role role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findByRole(User.Role role, Page page) throws DaoException {
        List<User> listUser = new ArrayList<>();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_USERS_BY_ROLE_LIMIT_PAGE);
            statement.setString(1, role.toString());
            statement.setString(2, User.State.ACTUAL.toString());
            statement.setString(3, User.State.BLOCKED.toString());
            statement.setInt(4, page.getFirst());
            statement.setInt(5, page.getMax());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next() && !resultSet.wasNull()) {
                User user = new User();
                user.setUserId(resultSet.getLong(TableColumnName.USER_DAO_ID));
                user.setLogin(resultSet.getString(TableColumnName.USER_DAO_LOGIN));
                user.setFirstName(resultSet.getString(TableColumnName.USER_DAO_FIRSTNAME));
                user.setLastName(resultSet.getString(TableColumnName.USER_DAO_LASTNAME));
                user.setRole(User.Role.valueOf(resultSet.getString(TableColumnName.USER_DAO_ROLE)));
                user.setState(User.State.valueOf(resultSet.getString(TableColumnName.USER_DAO_STATE)));
                user.setPhone(resultSet.getString(TableColumnName.USER_DAO_PHONE));
                user.setBalance(resultSet.getBigDecimal(TableColumnName.USER_DAO_BALANCE));
                if (resultSet.getBinaryStream(TableColumnName.USER_DAO_PHOTO) != null) {
                    InputStream inputStream = resultSet.getBinaryStream(TableColumnName.USER_DAO_PHOTO);
                    String photo = PictureUtil.imageToBase64(inputStream);
                    user.setPhoto(photo);
                }
                listUser.add(user);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);

        }
        return listUser;
    }

    @Override
    public Optional<User> login(String login, String password) {

        return null;
    }

    @Override
    public boolean updatePhoto(long userId, InputStream photo) throws DaoException {
        boolean isUpdate;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_USER_PHOTO);
            statement.setBlob(1, photo);
            statement.setLong(2, userId);
            int amountOfRows = statement.executeUpdate();
            isUpdate = amountOfRows > 0;
        } catch (SQLException ex) {
            throw new DaoException("SQL exception (request or table failed)", ex);
        } finally {
            statementClose(statement);

        }
        return isUpdate;
    }

    @Override
    public int countByRole(User.Role role) throws DaoException {
        int result = 0;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_USERS_BY_ROLE);
            statement.setString(1, role.toString());
            statement.setString(2, User.State.ACTUAL.toString());
            statement.setString(3, User.State.BLOCKED.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SqlQuery.COUNT_USERS);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);

        }
        return result;
    }

    public int create(User user, String hashPassword, String verificationCode) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        int result = -1;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_USER_WITH_PASSWORD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin() != null ? user.getLogin() : "");
            statement.setString(2, user.getLastName() != null ? user.getLastName() : "");
            statement.setString(3, user.getFirstName() != null ? user.getFirstName() : "");
            statement.setString(4, user.getPhone() != null ? user.getPhone() : "");
            statement.setString(5, user.getRole().toString());
            statement.setString(6, user.getState().toString());
            statement.setString(7, hashPassword);
            statement.setString(8, verificationCode);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }

    @Override
    public Optional<String> findVerificationCodeByLogin(String login) throws DaoException {
        Optional<String> result = Optional.empty();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_VERIFICATION_CODE_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String verificationCode = resultSet.getString(TableColumnName.USER_DAO_VERIFICATION_CODE);
                result = Optional.of(verificationCode);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }

    @Override
    public boolean updateState(long userId, User.State state) throws DaoException {
        boolean isUpdate;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_USER_STATE);
            statement.setLong(2, userId);
            statement.setString(1, state.toString());
            int amountOfRows = statement.executeUpdate();
            isUpdate = amountOfRows > 0;
        } catch (SQLException e) {
            throw new DaoException("SQL exception (request or table failed)", e);
        } finally {
            statementClose(statement);

        }
        return isUpdate;
    }

    @Override
    public boolean updatePassword(long userId, String newUserPassword) throws DaoException {
        boolean isUpdate;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_USER_PASSWORD);
            statement.setString(1, newUserPassword);
            statement.setLong(2, userId);
            int amountOfRows = statement.executeUpdate();
            isUpdate = amountOfRows > 0;
        } catch (SQLException e) {
            throw new DaoException("SQL exception (request or table failed)", e);
        } finally {
            statementClose(statement);

        }
        return isUpdate;
    }

    @Override
    public boolean addBalance(long userId, BigDecimal value) throws DaoException {
        boolean isUpdate;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_USER_BALANCE_ADD);
            statement.setLong(2, userId);
            statement.setBigDecimal(1, value);
            int amountOfRows = statement.executeUpdate();
            isUpdate = amountOfRows > 0;
        } catch (SQLException e) {
            throw new DaoException("SQL exception (request or table failed)", e);
        } finally {
            statementClose(statement);

        }
        return isUpdate;
    }
    @Override
    public boolean minusBalance(long userId, BigDecimal value) throws DaoException {
        boolean isUpdate;
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_USER_BALANCE_MINUS);
            statement.setLong(2, userId);
            statement.setBigDecimal(1, value);
            int amountOfRows = statement.executeUpdate();
            isUpdate = amountOfRows > 0;
        } catch (SQLException e) {
            throw new DaoException("SQL exception (request or table failed)", e);
        } finally {
            statementClose(statement);

        }
        return isUpdate;
    }

    @Override
    public List<User> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        Connection connection = this.connection;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong(TableColumnName.USER_DAO_ID));
                user.setLogin(resultSet.getString(TableColumnName.USER_DAO_LOGIN));
                user.setFirstName(resultSet.getString(TableColumnName.USER_DAO_FIRSTNAME));
                user.setLastName(resultSet.getString(TableColumnName.USER_DAO_LASTNAME));
                user.setRole(User.Role.valueOf(resultSet.getString(TableColumnName.USER_DAO_ROLE)));
                user.setState(User.State.valueOf(resultSet.getString(TableColumnName.USER_DAO_STATE)));
                user.setPhone(resultSet.getString(TableColumnName.USER_DAO_PHONE));
                user.setBalance(resultSet.getBigDecimal(TableColumnName.USER_DAO_BALANCE));
                if (resultSet.getBinaryStream(TableColumnName.USER_DAO_PHOTO) != null) {
                    InputStream inputStream = resultSet.getBinaryStream(TableColumnName.USER_DAO_PHOTO);
                    String photo = PictureUtil.imageToBase64(inputStream);
                    user.setPhoto(photo);
                }
                userOptional = Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return userOptional;

    }

    @Override
    public boolean delete(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        int result = -1;
        try {
            statement = connection.prepareStatement(SqlQuery.DELETE_USER_BY_ID);
            statement.setLong(1, id);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result > 0 ? true : false;
    }

    @Override
    public int create(User entity) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        int result = -1;
        try {
            statement = connection.prepareStatement(SqlQuery.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getLogin() != null ? entity.getLogin() : "");
            statement.setString(2, entity.getLastName() != null ? entity.getLastName() : "");
            statement.setString(3, entity.getFirstName() != null ? entity.getFirstName() : "");
            statement.setString(4, entity.getPhone() != null ? entity.getPhone() : "");
            statement.setString(5, entity.getRole().toString());
            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }

    @Override
    public int update(User user) throws DaoException {
        Connection connection = this.connection;
        PreparedStatement statement = null;
        int result = -1;
        try {
            statement = connection.prepareStatement(SqlQuery.UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhone());
            statement.setLong(4, user.getUserId());
            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            statementClose(statement);
        }
        return result;
    }
}