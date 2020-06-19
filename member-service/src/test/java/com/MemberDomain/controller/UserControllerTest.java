//package com.MemberDomain.controller;
//
//import com.MemberDomain.model.request.LoginRequest;
//import com.MemberDomain.model.request.RegisterRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    UserController userController;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    void contextLoads() throws Exception {
//        assertThat(userController).isNotNull();
//    }
//
//    @Test
//    void RegisterIntegratedTest() throws Exception{
//        RegisterRequest registerRequest = new RegisterRequest();
//
//        //successfully created user
//        registerRequest.setName("Aldie Adrian");
//        registerRequest.setEmail("aldie@gmail.com");
//        registerRequest.setPhoneNumber("+6281287878787");
//        registerRequest.setPassword("H0lmesHere!");
//        registerRequest.setConfirmPassword("H0lmesHere!");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isCreated());
//
//        //invalid name
//        registerRequest.setName("aldieadrian17");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isBadRequest());
//
//        //invalid email
//        registerRequest.setName("Aldie Adrian");
//        registerRequest.setEmail("aldieadriangmail.com");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isBadRequest());
//
//        //invalid phone number
//        registerRequest.setName("Aldie Adrian");
//        registerRequest.setEmail("aldie@gmail.com");
//        registerRequest.setPhoneNumber("+6280287878787");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isBadRequest());
//
//        //invalid password
//        registerRequest.setName("Aldie Adrian");
//        registerRequest.setEmail("aldie@gmail.com");
//        registerRequest.setPhoneNumber("+6281287878788");
//        registerRequest.setPassword("P@ssword");
//        registerRequest.setConfirmPassword("P@ssword");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isBadRequest());
//
//        //miss match password
//        registerRequest.setName("Aldie Adrian");
//        registerRequest.setEmail("aldie@gmail.com");
//        registerRequest.setPhoneNumber("+6281287878788");
//        registerRequest.setPassword("H0lmesHere!");
//        registerRequest.setConfirmPassword("P@ssword");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isBadRequest());
//
//        //email already exist
//        registerRequest.setName("Aldie Adrian");
//        registerRequest.setEmail("aldie@gmail.com");
//        registerRequest.setPhoneNumber("+6281287878788");
//        registerRequest.setPassword("H0lmesHere!");
//        registerRequest.setConfirmPassword("H0lmesHere!");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isBadRequest());
//
//        //phone number already exist
//        registerRequest.setName("Aldie Adrian");
//        registerRequest.setEmail("sherlock@gmail.com");
//        registerRequest.setPhoneNumber("+6281287878787");
//        registerRequest.setPassword("H0lmesHere!");
//        registerRequest.setConfirmPassword("H0lmesHere!");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void LoginIntegratedTest() throws Exception{
//
//        LoginRequest loginRequest = new LoginRequest();
//
//        //successfully logged in
//        loginRequest.setPhoneNumber("+6281287878787");
//        loginRequest.setPassword("H0lmesHere!");
//        mockMvc.perform(post("/api/auth/login")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk());
//
//        //invalid phone number
//        loginRequest.setPhoneNumber("+6280287878787");
//        mockMvc.perform(post("/api/auth/login")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isBadRequest());
//
//        //invalid password
//        loginRequest.setPhoneNumber("+6281287878787");
//        loginRequest.setPassword("P@ssword");
//        mockMvc.perform(post("/api/auth/login")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isBadRequest());
//
//        //wrong phone number
//        loginRequest.setPhoneNumber("+6281287878788");
//        loginRequest.setPassword("H0lmesHer3!");
//        mockMvc.perform(post("/api/auth/login")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isNotFound());
//
//        //wrong password
//        loginRequest.setPhoneNumber("+6281287878787");
//        loginRequest.setPassword("H0lmesHer3!");
//        mockMvc.perform(post("/api/auth/login")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isNotFound());
//
//    }
//}