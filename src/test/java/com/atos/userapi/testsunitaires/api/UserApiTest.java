package com.atos.userapi.testsunitaires.api;

import com.atos.userapi.api.UserApi;
import com.atos.userapi.entities.UserEntity;
import com.atos.userapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApi.class)
class UserApiTest {

    @MockBean
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private UserEntity createNewUser(String name) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername(name);
        user.setCountry("France");
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        user.setBirthdate(new Date(df.parse("12-12-1989").getTime()));

        return user;
    }

    @Test
    void getAll() throws Exception {
        List<UserEntity> users = new ArrayList<UserEntity>();

        int numberUser = 5;
        for(int i = 0; i < numberUser; i++){
            users.add(createNewUser("test getAll item : " + i));
        }

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(numberUser));
    }

    @Test
    void addUser() throws Exception {
        UserEntity user = createNewUser("test addUSer");

        when(userService.saveUser(any(UserEntity.class))).thenReturn(user);

        mockMvc.perform(post("/api/user")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));

    }

    @Test
    void getUser() throws Exception {
        UserEntity user = createNewUser("test addUSer");

        when(userService.findById(any(int.class))).thenReturn(user);

        mockMvc.perform(get("/api/user/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }
}