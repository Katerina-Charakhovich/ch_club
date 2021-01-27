package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.entity.Page;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional <User> findUserByLogin(String login) throws DaoException;
    Optional<String> findPasswordByLogin(String login) throws DaoException;
    int count(User.Role role) throws DaoException;
    List<User> findByRole(User.Role role);
    List<User> findByRole(User.Role role, Page page) throws DaoException;
    Optional <User> login(String login, String password);
    boolean updatePhoto(long userId, InputStream photo) throws DaoException;
    int countByRole(User.Role role) throws DaoException;
    int create(User user,String hashPassword,String verificationCode) throws DaoException;
    public Optional<String> findVerificationCodeByLogin(String login) throws DaoException ;
    boolean updateState(long userId , User.State state) throws DaoException;
    boolean updatePassword(long userId , String newUserPassword) throws DaoException;
}
