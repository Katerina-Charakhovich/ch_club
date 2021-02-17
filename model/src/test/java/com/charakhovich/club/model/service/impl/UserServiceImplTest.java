package com.charakhovich.club.model.service.impl;

import com.charakhovich.club.model.dao.impl.UserDaoImpl;
import com.charakhovich.club.model.entity.Page;
import com.charakhovich.club.model.entity.User;
import com.charakhovich.club.model.exeption.DaoException;
import com.charakhovich.club.model.exeption.ServiceException;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class UserServiceImplTest {
    UserServiceImpl userService;
    UserDaoImpl userDao;
    List<User> expected;

    @BeforeMethod
    public void setUp() {
        userDao = Mockito.mock(UserDaoImpl.class);
        userService = new UserServiceImpl();
        WhiteboxImpl.setInternalState(userService, "userDao", userDao);
        expected = new ArrayList<>();
    }

    @Test
    public void testCheckLogin() {
        String login = "my@tut.by";
        String password = "123";
        try {
            Optional<String> correctPassword = Optional.of("$2a$10$xLqQDLDQvp8JzU0.R5c6e.3T8OdpFhTl/m0LSdRYhChCNaCbz/NwS");
            when(userDao.findPasswordByLogin(login)).thenReturn(correctPassword);
            boolean actual = userService.checkLogin(login, password);
            assertTrue(actual);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testFindUserByLogin() {
        try {
            String login = "my@tut.by";
            User user = new User("TestName", "TestLastName", "123", "my@tut.by", User.Role.USER, User.State.ACTUAL);
            String password = "123";
            when(userDao.findUserByLogin(Mockito.anyString())).thenReturn(Optional.of(user));
            Optional<User> actualUser = userService.findUserByLogin(login);
            assertEquals(user, actualUser.get());
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByRole() {
        try {
            Page page=new Page(1,5);
            User.Role role=User.Role.USER;
            when(userDao.findByRole(role,page)).thenReturn(expected);
            List<User> actual = userService.findByRole(role,page);
            assertEquals(actual, expected);
        } catch (ServiceException | DaoException e) {
            fail("Incorrect data", e);
        }
    }

    @Test
    public void testAddBalance()  {
        try {
            long userId = 1;
            BigDecimal amount = BigDecimal.valueOf(150, 23);
            when(userDao.addBalance(userId, amount)).thenReturn(true);
            assertEquals(true, userService.addBalance(userId, amount));
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testUpdateState() {
        try {
            when(userDao.updateState(1, User.State.ACTUAL)).thenReturn(true);
            assertEquals(true, userService.updateState(1, User.State.ACTUAL));
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }
}