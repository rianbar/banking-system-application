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
    private final Long id = 14L; //could be a real id in database
    private final Long unavailableId = 500L;
    private String jsonRequest;
    private String jsonUpdate;
    private String jsonLogin;
    private String email;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwcGxpY2F0aW9uIiwic3ViIjoiZ2V0Y29kZUBlbWFpbC5jb" +
                "20iLCJleHAiOjE3MDgwMjA2NjJ9.NWyXjXKHBUmnIITtC_VLaCxfgLUBdLF4LCO8IN31CkU";

        email = "anyone12@email.com"; // alter email always you run the test class
        String identity = "115.325.555-13"; // alter identity always you run the test class
        jsonRequest = "{\"name\":\"any\",\"identity\":\"" + identity + "\"," +
                "\"email\":\"" + email + "\",\"password\":\"any\",\"userType\":\"USER\"}";
        jsonUpdate = "{\"name\":\"any\",\"identity\":\"090.224.667-09\"," +
                "\"email\":\"updateMail@email.com\",\"password\":\"any\",\"userType\":\"USER\"}";

        jsonLogin = "{\"email\":\"" + email + "\",\"password\":\"any\"}";
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
                        .content(jsonLogin))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));
    }

    @Test
    void loginUser_withInvalidUser_throwException() throws Exception {
        String expectedJson = "{\"code\":401,\"type\":\"401 UNAUTHORIZED\",\"message\":\"email or password is wrong!\"}";

        mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"wrongEmail@email.com\",\"password\": \"wrong\"}"))
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
                .contentType(MediaType.APPLICATION_JSON).content(jsonUpdate))
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

    @Test
    void findUserByEmail_withValidData_returnsObject() throws Exception {
        mvc.perform(get("/user/details/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findUserByEmail_withValidData_throwException() throws Exception {
        String expectedJson = "{\"code\":404,\"type\":\"404 NOT_FOUND\",\"message\":\"user not found!\"}";

        mvc.perform(get("/user/details/{email}", "wrongEmail@email.com"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }
}
