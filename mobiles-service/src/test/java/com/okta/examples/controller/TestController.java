package com.okta.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.examples.model.request.RegisterRequest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void authRegister() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("kevin");
        registerRequest.setEmail("kevinard11@gmail.com");
        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("P@ssw0rd");
        registerRequest.setConfirmPassword("P@ssw0rd");

        mockMvc.perform(post("/api/auth/register", 9)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void authLogin() throws Exception{

        JSONObject json = new JSONObject();
        json.put("phoneNumber", "+6287819411111");
        json.put("password", "P@ssw0rd");
        mockMvc.perform(post("/api/auth/login", 9)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

    }

    @Test
    void userGetProfile() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTA3NjlkNi01Y2Q4LTQ3MmYtOGU3OS0yMmVjMTY3YjNlYWExMSIsImV4cCI6MTU5MzA2OTY2MywiaWF0IjoxNTkyNDU0NzgzfQ.FggRkWOTI2Ijrq06Bkm_RZStz6KlZFrfI7aLZqmO2wgFo4QodEIytwbsGT6Di8McEK3vnIK29T5Fsu06qj3Yng");
        mockMvc.perform(get("/api/user/{idUser}", 11)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

        headers.set("Authorization", "Bearer yJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTA3NjlkNi01Y2Q4LTQ3MmYtOGU3OS0yMmVjMTY3YjNlYWExMSIsImV4cCI6MTU5MzA2OTY2MywiaWF0IjoxNTkyNDU0NzgzfQ.FggRkWOTI2Ijrq06Bkm_RZStz6KlZFrfI7aLZqmO2wgFo4QodEIytwbsGT6Di8McEK3vnIK29T5Fsu06qj3Yng");
        mockMvc.perform(get("/api/user/{idUser}", 11)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTA3NjlkNi01Y2Q4LTQ3MmYtOGU3OS0yMmVjMTY3YjNlYWExMSIsImV4cCI6MTU5MzA2OTY2MywiaWF0IjoxNTkyNDU0NzgzfQ.FggRkWOTI2Ijrq06Bkm_RZStz6KlZFrfI7aLZqmO2wgFo4QodEIytwbsGT6Di8McEK3vnIK29T5Fsu06qj3Yn");
        mockMvc.perform(get("/api/user/{idUser}", 11)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void userEditProfile() throws Exception{

        JSONObject json = new JSONObject();
        json.put("name", "kevin");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTA3NjlkNi01Y2Q4LTQ3MmYtOGU3OS0yMmVjMTY3YjNlYWExMSIsImV4cCI6MTU5MzA2OTY2MywiaWF0IjoxNTkyNDU0NzgzfQ.FggRkWOTI2Ijrq06Bkm_RZStz6KlZFrfI7aLZqmO2wgFo4QodEIytwbsGT6Di8McEK3vnIK29T5Fsu06qj3Yng");
        mockMvc.perform(put("/api/user/{idUser}", 11)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

        headers.set("Authorization", "Bearer ");
        mockMvc.perform(put("/api/user/{idUser}", 11)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }





}
