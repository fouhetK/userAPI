package com.atos.userapi.testsunitaires.services;

import com.atos.userapi.entities.UserEntity;
import com.atos.userapi.repositories.UserRepository;
import com.atos.userapi.services.UserService;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    private UserEntity createNewUser(String name) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername(name);
        user.setCountry("France");
        user.setBirthdate(new Date(df.parse("12-12-1989").getTime()));

        return user;
    }

    private UserEntity updateUser(UserEntity user){
        user.setId(user.getId() + 1);
        return user;
    }

    @Test
    public void findAll() throws ParseException {
        int numberUser = 5;
        List<UserEntity> users = new ArrayList<UserEntity>();
        UserEntity userTmp;

        for (int i = 0; i < numberUser; i++){
            userTmp = createNewUser("test find all n°" + i);
            users.add(userTmp);
        }

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(IterableUtil.sizeOf(userService.findAll()), numberUser);
    }

    @Test
    public void findById() throws ParseException {
        UserEntity user = createNewUser("test findById");

        when(userRepository.findById(any(int.class))).thenReturn(java.util.Optional.of(user));

        assertNotNull(userService.findById(user.getId()));
    }

    @Test
    public void updateUser() throws ParseException, InvalidObjectException {
        UserEntity userFind = createNewUser("test save");
        UserEntity userUpdate = createNewUser("test update");

        when(userRepository.findById(any(int.class))).thenReturn(java.util.Optional.of(userFind));

        when(userRepository.save(any(UserEntity.class))).thenReturn(userUpdate);

        UserEntity user = userService.updateUser(0, userUpdate);

        assertEquals(user.getUsername(), "test update");
    }

    @Test
    public void saveUser() throws InvalidObjectException, ParseException {
        UserEntity user = createNewUser("test save");

        when(userRepository.save(any(UserEntity.class))).thenReturn(updateUser(user));

        assertNotEquals(userService.saveUser(user).getId(), 0);
    }

    //test for the verification of the variable

    @Test
    public void saveUserNoBirthdate() throws ParseException {
        UserEntity user = createNewUser("test save");

        user.setBirthdate(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
            //throw new InvalidObjectException("The user must have a birthdate (yyyy-mm-dd)");
        });
    }

    @Test
    public void saveUserNoUsername() throws ParseException {
        UserEntity user = createNewUser("test save");

        user.setUsername(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
            //throw new InvalidObjectException("The user must have a username");
        });
    }

    @Test
    public void saveUserNoCountry() throws ParseException {
        UserEntity user = createNewUser("test save");

        user.setCountry(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
            //throw new InvalidObjectException("The user must have a country");
        });
    }

    @Test
    public void saveUserInvalidPhone() throws ParseException {
        UserEntity user = createNewUser("test save");

        user.setPhone("05 06 04");

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
            //throw new InvalidObjectException("Phone number is invalid");
        });
    }

    @Test
    public void saveUserWrongCountry() throws ParseException {
        UserEntity user = createNewUser("test save");
        user.setCountry("England");

//        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
            //throw new InvalidObjectException("The user must be from France to be registered");
        });
    }

    @Test
    public void saveUserWrongAge() throws ParseException {
        UserEntity user = createNewUser("test save");

        user.setBirthdate(new Date(df.parse("12-12-2010").getTime()));

//        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
            //throw new InvalidObjectException("The user must be an adult (above 18 years old) to be registered");
        });
    }

}