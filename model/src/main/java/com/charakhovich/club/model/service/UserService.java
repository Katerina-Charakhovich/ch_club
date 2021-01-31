package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The interface User service.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface UserService {
    /**
     * Checks user by login, password
     *
     * @param login    the email
     * @param password the password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkLogin(String login, String password) throws ServiceException;

    /**
     * Registers user.
     *
     * @param user             the user
     * @param password         the password
     * @param verificationCode the verification code
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> registry(User user, String password, String verificationCode) throws ServiceException;

    /**
     * Finds user by login
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByLogin(String login) throws ServiceException;

    /**
     * Updates user photo.
     *
     * @param userId the user id
     * @param photo  the photo
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updatePhoto(long userId, InputStream photo) throws ServiceException;

    /**
     * Finds list user by role limited limited to a page
     *
     * @param role the user role
     * @param page the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findByRole(User.Role role, Page page) throws ServiceException;

    /**
     * Returns count user by role
     *
     * @param role the user role
     * @return the int
     * @throws ServiceException the service exception
     */
    int countByRole(User.Role role) throws ServiceException;

    /**
     * Returns count user by role
     *
     * @param login            the login
     * @param verificationCode the verification code
     * @return the int
     * @throws ServiceException the service exception
     */
    boolean confirmRegistry(String login, String verificationCode) throws ServiceException;

    /**
     * Confirms user registration.
     *
     * @param userId the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean delete(long userId) throws ServiceException;

    /**
     * Updates user password .
     *
     * @param userId      the user id
     * @param newPassword the new password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updatePassword(long userId, String newPassword) throws ServiceException;

    /**
     * Updates user info.
     *
     * @param user the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(User user) throws ServiceException;

    /**
     * Adds balance.
     *
     * @param userId the user id
     * @param amount the amount
     * @throws ServiceException the service exception
     */
    boolean addBalance(long userId, BigDecimal amount) throws ServiceException;

    /**
     * Changes user state.
     *
     * @param userId the user id
     * @param state  the state user
     * @throws ServiceException the service exception
     */
    boolean updateState(long userId, User.State state) throws ServiceException;

}
