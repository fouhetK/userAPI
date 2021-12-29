package com.atos.userapi.testsintegrationcomposants.api;

import com.atos.userapi.entities.UserEntity;
import com.atos.userapi.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    private UserEntity createNewUser(String name) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername(name);
        user.setCountry("France");

        user.setBirthdate(new Date(df.parse("12-12-1989").getTime()));

        return user;
    }

    @Test
    public void getAll() throws Exception {
        int numberUsers = 5;

        for (int i = 0; i < numberUsers; i++)
            userRepository.save(createNewUser("test getAll " + i));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.greaterThanOrEqualTo(numberUsers)));
    }

    @Test
    public void addUser() throws Exception {
        UserEntity user = createNewUser("test addUser");

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    public void addUserWithoutUsername() throws Exception {
        UserEntity user = createNewUser("test addUser");

        user.setUsername(null);

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The user must have a username"));
    }

    @Test
    public void addUserWithoutCountry() throws Exception {
        UserEntity user = createNewUser("test addUser");

        user.setCountry(null);

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The user must have a country"));
    }

    @Test
    public void addUserWithoutBirthdate() throws Exception {
        UserEntity user = createNewUser("test addUser");

        user.setBirthdate(null);

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The user must have a birthdate (yyyy-mm-dd)"));
    }


    @Test
    public void addUserInvalidPhone() throws Exception {
        UserEntity user = createNewUser("test addUser");

        user.setPhone("05 06 04");

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Phone number is invalid"));
    }

    @Test
    public void addUserWrongCountry() throws Exception {
        UserEntity user = createNewUser("test addUser");

        user.setCountry("England");

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The user must be from France to be registered"));
    }

    @Test
    public void addUserWrongAge() throws Exception {
        UserEntity user = createNewUser("test addUser");

        user.setBirthdate(new Date(df.parse("12-12-2010").getTime()));

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("The user must be an adult (above 18 years old) to be registered"));
    }

    @Test
    public void getUser() throws Exception {
        UserEntity user = createNewUser("test addUser");

        userRepository.save(user);

        mockMvc.perform(get("/api/user/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test addUser"));
    }

    @Test
    public void getUserNotFound() throws Exception {
        mockMvc.perform(get("/api/user/0"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("User not found"));
    }
}