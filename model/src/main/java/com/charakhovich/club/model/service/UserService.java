package com.charakhovich.club.model.service;

import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface UserService {
     boolean checkLogin (String login,String password) throws ServiceException;
     Optional<User> registry(User user, String userPassword,String verificationCode)throws ServiceException;
     Optional<User> findUserByLogin(String login) throws ServiceException;
     boolean updatePhoto(long userId, InputStream photo) throws ServiceException;
     List<User> findByRole(User.Role role, Page page) throws ServiceException;
     int countByRole(User.Role role) throws ServiceException;
     boolean confirmRegistry(String userLogin, String verificationCode) throws ServiceException;
     boolean delete(long userId) throws ServiceException;
     boolean updatePassword(long userId,String newUserPassword) throws ServiceException;
     boolean update(User user) throws ServiceException;

}
