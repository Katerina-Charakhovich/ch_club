package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.EntityTransaction;
import com.charakhovich.club.model.dao.impl.TicketDaoImpl;
import com.charakhovich.club.model.dao.impl.UserDaoImpl;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.Ticket;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.util.Encryptor;
import com.charakhovich.club.model.util.PictureUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserDaoImpl userDao;
    private TicketDaoImpl ticketDao;

    public UserServiceImpl() {

        userDao = new UserDaoImpl();
        ticketDao = new TicketDaoImpl();
    }

    private static final int PHOTO_WIDTH = 200;

    @Override
    public boolean checkLogin(String login, String password) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initSingleQuery(userDao);
            Optional<String> tempPassword = Optional.empty();
            boolean result = false;
            tempPassword = userDao.findPasswordByLogin(login);
            if (tempPassword.isPresent()) {
                String passwordString = tempPassword.get();
                result = Encryptor.checkPassword(password, passwordString);
            }
            return result;
        } catch (DaoException daoException) {
            throw new ServiceException("Error rollback transaction", daoException);
        } finally {
            transaction.endSingleQuery();
        }

    }

    @Override
    public Optional<User> registry(User user, String userPassword, String verificationCode) throws ServiceException {
        Optional<User> userResult = Optional.empty();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initTransaction(userDao);
            Optional<User> userFindByLoginOptional = userDao.findUserByLogin(user.getLogin());
            boolean findUser = userFindByLoginOptional.isPresent() ? userFindByLoginOptional.get().getState() != User.State.DELETED ? true : false : false;
            if (!findUser) {
                String hashPassword = Encryptor.hashPassword(userPassword);
                int i = userDao.create(user, hashPassword, verificationCode);
                userResult = userDao.findUserByLogin(user.getLogin());
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
        return userResult;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        Optional<User> userResult = Optional.empty();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(userDao);
        try {
            userResult = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return userResult;
    }

    @Override
    public boolean updatePhoto(long userId, InputStream photo) throws ServiceException {
        boolean isUpdate = false;
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(userDao);
        try {
            InputStream newPhoto = PictureUtil.resizeImage(photo, PHOTO_WIDTH);
            isUpdate = userDao.updatePhoto(userId,newPhoto);
        } catch (DaoException ex) {
            try {
                transaction.rollback();
            } catch (DaoException e) {
                e.printStackTrace();
            }
            throw new ServiceException("Error during download photo", ex);
        } finally {
            transaction.endSingleQuery();
        }

        return isUpdate;
    }

    @Override
    public List<User> findByRole(User.Role role, Page page) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(userDao);
        List<User> result;
        try {
            result = userDao.findByRole(role, page);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }

    @Override
    public int countByRole(User.Role role) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(userDao);
        int result = 0;
        try {
            result = userDao.countByRole(role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return result;
    }

    @Override
    public boolean confirmRegistry(String login, String verificationCode) throws ServiceException {
        boolean result = false;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initTransaction(userDao);

            Optional<String> verificationCodeFromBase = userDao.findVerificationCodeByLogin(login);
            if (verificationCodeFromBase.isPresent()) {
                result = verificationCode.equals(verificationCodeFromBase.get());
                if (result) {
                    Optional<User> user = userDao.findUserByLogin(login);
                    userDao.updateState(user.get().getUserId(), User.State.ACTUAL);
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
    public boolean delete(long userId) throws ServiceException {
        boolean result = false;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initTransaction(userDao);
            result = userDao.delete(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result;
    }

    @Override
    public boolean updatePassword(long userId, String newUserPassword) throws ServiceException {
        boolean result = false;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initTransaction(userDao);
            result = userDao.updatePassword(userId, newUserPassword);
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
    public List<Ticket> findUserTickets(long userId) throws ServiceException {
        List<Ticket> tickets = new ArrayList<>();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initSingleQuery(ticketDao);
        try {
            tickets = ticketDao.findTicketUser(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.endSingleQuery();
        }
        return tickets;
    }

    @Override
    public boolean update(User user) throws ServiceException {
        int result = -1;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initSingleQuery(userDao);
            result = userDao.update(user);
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
        return result > 0 ? true : false;
    }

    @Override
    public boolean addBalance(long userId, BigDecimal amount) throws ServiceException {
        boolean result = false;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initSingleQuery(userDao);
            result = userDao.addBalance(userId, amount);
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
    public boolean updateState(long userId, User.State state) throws ServiceException {
        boolean result = false;
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.initSingleQuery(userDao);

            result = userDao.updateState(userId, state);
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
}

