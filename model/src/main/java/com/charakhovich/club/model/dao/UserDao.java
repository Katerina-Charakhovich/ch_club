package com.charakhovich.club.model.dao;

import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.entity.Page;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public interface UserDao {
    /**
     * Finds user by login optional.
     * Returns password, but if user isn't found returns null
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Finds password by login optional.
     * Returns password, but if user isn't found returns null
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findPasswordByLogin(String login) throws DaoException;

    /**
     * Returns the count of users by role
     *
     * @param role the role
     * @return the int
     * @throws DaoException the dao exception
     */
    int count(User.Role role) throws DaoException;

    /**
     * Returns a list of users by role by role
     *
     * @param role the role
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByRole(User.Role role);

    /**
     * Returns a list of users by role by role
     *
     * @param role the role
     * @param page the  page
     * @return the list
     * @throws DaoException the dao exception
     */

    List<User> findByRole(User.Role role, Page page) throws DaoException;

    /**
     * Finds user by login and by password optional.
     * Returns user, but if user isn't found returns null
     *
     * @param login    the login
     * @param password the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> login(String login, String password) throws DaoException;

    /**
     * Updates user photo.
     *
     * @param userId the user id
     * @param photo  the photo
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePhoto(long userId, InputStream photo) throws DaoException;

    /**
     * Returns count of users by role
     *
     * @param role the user role
     * @return the int
     * @throws DaoException the dao exception
     */
    int countByRole(User.Role role) throws DaoException;
    /**
     * Returns count of users by role
     *
     * @param role the user role
     * @param subLastname the substring of lastname
     * @return the int
     * @throws DaoException the dao exception
     */
    int countByRoleByLastname(User.Role role,String subLastname) throws DaoException;
    /**
     * Creates new user in state
     *
     * @param user             the user
     * @param hashPassword     the password
     * @param verificationCode the verificationCode
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(User user, String hashPassword, String verificationCode) throws DaoException;

    /**
     * Creates new user in state
     *
     * @param login the login user
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findVerificationCodeByLogin(String login) throws DaoException;

    /**
     * Changes user state.
     *
     * @param userId the user id
     * @param state  the state of user
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateState(long userId, User.State state) throws DaoException;

    /**
     * Changes user password.
     *
     * @param userId          the user id
     * @param newUserPassword the new password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePassword(long userId, String newUserPassword) throws DaoException;

    /**
     * Increases user balance
     *
     * @param userId the user id
     * @param amount the amount for add
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean addBalance(long userId, BigDecimal amount) throws DaoException;

    /**
     * Decreases user balance
     *
     * @param userId the user id
     * @param amount the amount for add
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean minusBalance(long userId, BigDecimal amount) throws DaoException;

    /**
     * Finds list user by lastname limited by page.
     * Returns user, but if user isn't found returns null
     *
     * @param role     the role
     * @param lastname the lastname
     * @param page     the  page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByRoleByLastname(User.Role role, String lastname, Page page) throws DaoException;


}
