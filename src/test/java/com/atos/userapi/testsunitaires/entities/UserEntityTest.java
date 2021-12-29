package com.atos.userapi.testsunitaires.entities;

import com.atos.userapi.entities.UserEntity;
import com.atos.userapi.enumerateur.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
    }

    @Test
    void getId() {
        assertEquals(this.user.getId(), 0);
    }

    @Test
    void setId() {
        int newId = 5;

        this.user.setId(newId);

        assertEquals(this.user.getId(), newId);
    }

    @Test
    void getUsername() {
        assertNull(this.user.getUsername());
    }

    @Test
    void setUsername() {
        String newUsername = "test";

        this.user.setUsername(newUsername);

        assertEquals(this.user.getUsername(), newUsername);
    }

    @Test
    void getBirthdate() {
        assertNull(this.user.getBirthdate());
    }

    @Test
    void setBirthdate() throws ParseException {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date(df.parse("12-12-1989").getTime());

        user.setBirthdate(date);

        assertEquals(user.getBirthdate(), date);
    }

    @Test
    void getCountry() {
        assertNull(this.user.getCountry());
    }

    @Test
    void setCountry() {
        String newCountry = "France";

        this.user.setCountry(newCountry);

        assertEquals(this.user.getCountry(), newCountry);
    }

    @Test
    void getPhone() {
        assertNull(this.user.getPhone());
    }

    @Test
    void setPhone() {
        String newPhone = "01 01 01 01 01";

        this.user.setPhone(newPhone);

        assertEquals(this.user.getPhone(), newPhone);
    }

    @Test
    void getGender() {
        assertNull(this.user.getGender());
    }

    @Test
    void setGender() {
        Gender newGender = Gender.M;

        this.user.setGender(newGender);

        assertEquals(this.user.getGender(), newGender);
    }
}