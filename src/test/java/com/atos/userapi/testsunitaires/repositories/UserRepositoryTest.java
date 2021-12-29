package com.atos.userapi.testsunitaires.repositories;

import com.atos.userapi.entities.UserEntity;
import com.atos.userapi.repositories.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private UserEntity createNewUser(String name) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername(name);
        user.setCountry("France");
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        user.setBirthdate(new Date(df.parse("12-12-1989").getTime()));

        return user;
    }

    @Test
    public void findAll() throws ParseException {
        int numberUser = 5;
        UserEntity userTmp;

        for (int i = 0; i < numberUser; i++) {
            userTmp = createNewUser("test find all n°" + i);
            userRepository.save(userTmp);
        }

        assertTrue(IterableUtil.sizeOf(userRepository.findAll()) >= numberUser);
    }

    @Test
    public void save() throws ParseException {
        UserEntity user = createNewUser("test user");

        user = userRepository.save(user);

        assertNotEquals(user.getId(), 0);
    }

    @Test
    public void updateUser() throws ParseException {

        UserEntity user = userRepository.save(createNewUser("test save"));

        user.setUsername("test update");

        UserEntity userUpdate = userRepository.save(user);

        assertEquals(userRepository.findById(user.getId()).get().getId(), userUpdate.getId());
    }

    @Test
    public void findById() throws ParseException, InvalidObjectException {
        UserEntity user = createNewUser("test findById");

        userRepository.save(user);

        assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    public void deleteById() throws ParseException {
        UserEntity user = createNewUser("test delete");

        userRepository.save(user);

        System.out.println("user id : " + user.getId());
        userRepository.deleteById(user.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            userRepository.deleteById(user.getId());
        });
    }
}