package com.bank.registersystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private String token;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwcGxpY2F0aW9uIiwic3ViIjoibWFyaWFuYXJhYnVkYWdv" +
                "c3Rvc2FAZW1haWwuY29tIiwiZXhwIjoxNzA3ODUwNDMzfQ.L9NRzL1X8btu0YsNS97Q0Jmdzali4fxXCuXj334hKlI";
    }

    @Test
    void getUserById_withValidData_returnsObject() throws Exception {
        mvc.perform(get("/user/{id}", 3)
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUserById_withInvalidData_throwsException() throws Exception {
        String expectedJson = "{\"code\":404,\"type\":\"404 NOT_FOUND\",\"message\":\"user 'id' not found!\"}";

        mvc.perform(get("/user/{id}", 500)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedJson));
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
}
