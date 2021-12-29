package com.atos.userapi.testsintegrationcomposants.services;

import com.atos.userapi.services.UserService;
import com.atos.userapi.entities.UserEntity;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    private UserEntity createNewUser(String name) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername(name);
        user.setCountry("France");
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        user.setBirthdate(new Date(df.parse("12-12-1989").getTime()));

        return user;
    }

    @Test
    public void findAll() throws ParseException, InvalidObjectException {
        int numberUser = 5;
        UserEntity userTmp;

        for (int i = 0; i < numberUser; i++) {
            userTmp = createNewUser("test find all n°" + i);
            userService.saveUser(userTmp);
        }

        assertTrue(IterableUtil.sizeOf(userService.findAll()) >= numberUser);
    }

    @Test
    public void save() throws ParseException, InvalidObjectException {
        UserEntity user = createNewUser("test user");

        user = userService.saveUser(user);

        assertNotEquals(user.getId(), 0);
    }

    @Test
    public void updateUser() throws ParseException, InvalidObjectException {

        UserEntity user = userService.saveUser(createNewUser("test save"));

        user.setUsername("test update");

        userService.updateUser(user.getId(), user);

        assertEquals(userService.findById(user.getId()).getUsername(), "test update");
    }

    @Test
    public void findById() throws ParseException, InvalidObjectException {
        UserEntity user = createNewUser("test findById");

        userService.saveUser(user);

        assertNotNull(userService.findById(user.getId()));
    }

    @Test
    public void deleteById() throws ParseException, InvalidObjectException {
        UserEntity user = createNewUser("test delete");

        userService.saveUser(user);

        System.out.println("user id : " + user.getId());
        userService.deleteById(user.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            userService.deleteById(user.getId());
        });
    }
}