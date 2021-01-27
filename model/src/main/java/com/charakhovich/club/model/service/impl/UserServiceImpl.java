package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.EntityTransaction;
import com.charakhovich.club.model.dao.impl.UserDaoImpl;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import com.charakhovich.club.model.service.UserService;
import com.charakhovich.club.model.util.Encryptor;
import com.charakhovich.club.model.util.PictureUtil;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Override
    public boolean checkLogin(String login, String password) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        Optional<String> tempPassword = Optional.empty();
        boolean result = false;
        try {
            tempPassword = userDao.findPasswordByLogin(login);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        if (tempPassword.isPresent()){
            String passwordString=tempPassword.get();
            result =   Encryptor.checkPassword(password,passwordString);
        }
        return result;
    }

    @Override
    public Optional<User> registry(User user, String userPassword, String verificationCode) throws ServiceException {
        Optional<User> userResult = Optional.empty();
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao);
        try {
            Optional<User> userFindByLoginOptional = userDao.findUserByLogin(user.getLogin());
            boolean findUser= userFindByLoginOptional.isPresent()?userFindByLoginOptional.get().getState()!=User.State.DELETED?true:false:false;
            if (!findUser) {
                String hashPassword = Encryptor.hashPassword(userPassword);
                int i = userDao.create(user, hashPassword, verificationCode);
                userResult = userDao.findUserByLogin(user.getLogin());
            }
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return userResult;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        Optional<User> userResult = Optional.empty();
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            userResult = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return userResult;
    }

    @Override
    public boolean updatePhoto(long userId, InputStream photo) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        boolean isUpdate = false;
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);

        try {
            InputStream newPhoto= PictureUtil.resizeImage(photo,200);
            isUpdate = userDao.updatePhoto(userId, photo);
        } catch (DaoException ex) {
            transaction.rollback();
            throw new ServiceException("Error during download photo", ex);
        } finally {
            transaction.end();
        }

        return isUpdate;
    }

    @Override
    public List<User> findByRole(User.Role role, Page page) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        List<User> result;
        try {
            result = userDao.findByRole(role, page);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public int countByRole(User.Role role) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        int result = 0;
        try {
            result = userDao.countByRole(role);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public boolean confirmRegistry(String login, String verificationCode) throws ServiceException {
        boolean result = false;
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao);
        try {
            Optional<String> verificationCodeFromBase = userDao.findVerificationCodeByLogin(login);
            if (verificationCodeFromBase.isPresent()) {
                result = verificationCode.equals(verificationCodeFromBase.get());
                if (result) {
                    Optional <User> user=userDao.findUserByLogin(login);
                    userDao.updateState(user.get().getUserId(),User.State.ACTUAL);
                }
            }
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result;
    }

    @Override
    public boolean delete(long userId) throws ServiceException {
        boolean result = false;
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao);
        try {
                result = userDao.delete(userId);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result;
    }

    @Override
    public boolean updatePassword(long userId, String newUserPassword) throws ServiceException {
        boolean result = false;
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao);
        try {
            result = userDao.updatePassword(userId,newUserPassword);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result;
    }

    @Override
    public boolean update(User user) throws ServiceException {
        int result=-1;
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao);
        try {
            result=userDao.update(user);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return result>0?true:false;
    }
}

