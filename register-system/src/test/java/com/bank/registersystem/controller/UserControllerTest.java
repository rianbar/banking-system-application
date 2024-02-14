package com.bank.registersystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private String token;
    private final Long id = 1L;
    private final Long unavailableId = 500L;
    private String jsonRequest;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwcGxpY2F0aW9uIiwic3ViIjoiYW55MTJAZ" +
                "W1haWwuY29tIiwiZXhwIjoxNzA3OTM3MDc2fQ.XMmW_bCcJotPjYU9PIW0QASx6zkDF9c4PkOB3qs5fMQ";

        String email = "anyone@email.com";
        String identity = "122.323.555-13";
        jsonRequest = "{\"name\":\"any\",\"identity\":\"" + identity + "\"," +
                "\"email\":\"" + email + "\",\"password\":\"any\",\"userType\":\"USER\"}";
    }

    @Test
    void createUser_withValidData_returnsObject() throws Exception {
        mvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createUser_withInvalidData_throwException() throws Exception {
        mvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginUser_withValidUser_returnToken() throws Exception {
        mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"rianbarroso1@email.com\",\"password\": \"riansantos\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));
    }

    @Test
    void loginUser_withInvalidUser_throwException() throws Exception {
        String expectedJson = "{\"code\":401,\"type\":\"401 UNAUTHORIZED\",\"message\":\"email or password is wrong!\"}";

        mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"any@email.com\",\"password\": \"any\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getUserById_withValidData_returnsObject() throws Exception {
        mvc.perform(get("/user/{id}", id)
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUserById_withInvalidData_throwsException() throws Exception {
        String expectedJson = "{\"code\":404,\"type\":\"404 NOT_FOUND\",\"message\":\"user 'id' not found!\"}";

        mvc.perform(get("/user/{id}", unavailableId)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedJson));
    }



    @Test
    void updateUser_withValidData_returnsObject() throws Exception {
        mvc.perform(put("/user/update/{id}", id)
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteUserTest() throws Exception {
        mvc.perform(delete("/user/delete/{id}", id)
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUserTest_withInvalidData() throws Exception {
        mvc.perform(delete("/user/delete/{id}", unavailableId)
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

}
