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

        mockMvc.perform(get("/")
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

    }

    @Test
    void userGetProfile() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/user/{idUser}", 23)
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
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(put("/api/user/{idUser}", 23)
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

    @Test
    void userCreateOrderVoucher() throws Exception{

        JSONObject json = new JSONObject();
        json.put("idVoucher",1);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(post("/api/user/{idUser}/transaction/voucher", 23)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isCreated());

        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2M2U4Yzg5My05ZDExLTQ2ZjEtOWM1Yi00NDUyOTExYTEyNTQyNCIsImV4cCI6MTU5MjczMzk1OSwiaWF0IjoxNTkyNzMzOTUwfQ.dbRlJGGZEeiAcn3PlynZfS_PMPiY-99uWKozE_KBSatF0iBwvRtYOBSVGYdOxIhG9dbQ_ytuzjEYyIiLsvZ2fw");
        mockMvc.perform(post("/api/user/{idUser}/transaction/voucher", 23)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void userPayOrderVoucher() throws Exception{

        JSONObject json = new JSONObject();
        json.put("idTransaction", 12);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(put("/api/user/{idUser}/transaction/voucher", 23)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void userTopup() throws Exception{

        JSONObject json = new JSONObject();
        json.put("virtualNumber", "90300878787877");
        json.put("amount", 5000);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(post("/api/user/{idUser}/transaction/topup", 23)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void userTransactionHistory() throws Exception{

        JSONObject json = new JSONObject();
        json.put("name", "kevin");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/user/{idUser}/transaction", 23)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTA3NjlkNi01Y2Q4LTQ3MmYtOGU3OS0yMmVjMTY3YjNlYWExMSIsImV4cCI6MTU5MzA2OTY2MywiaWF0IjoxNTkyNDU0NzgzfQ.FggRkWOTI2Ijrq06Bkm_RZStz6KlZFrfI7aLZqmO2wgFo4QodEIytwbsGT6Di8McEK3vnIK29T5Fsu06qj3Yng");
        mockMvc.perform(get("/api/user/{idUser}/transaction/12", 12)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/user/{idUser}/transaction/12", 12)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void userTransactionDetail() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTA3NjlkNi01Y2Q4LTQ3MmYtOGU3OS0yMmVjMTY3YjNlYWExMSIsImV4cCI6MTU5MzA2OTY2MywiaWF0IjoxNTkyNDU0NzgzfQ.FggRkWOTI2Ijrq06Bkm_RZStz6KlZFrfI7aLZqmO2wgFo4QodEIytwbsGT6Di8McEK3vnIK29T5Fsu06qj3Yng");
        mockMvc.perform(get("/api/user/{idUser}/transaction/{idTransaction}}", 23,12)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTA3NjlkNi01Y2Q4LTQ3MmYtOGU3OS0yMmVjMTY3YjNlYWExMSIsImV4cCI6MTU5MzA2OTY2MywiaWF0IjoxNTkyNDU0NzgzfQ.FggRkWOTI2Ijrq06Bkm_RZStz6KlZFrfI7aLZqmO2wgFo4QodEIytwbsGT6Di8McEK3vnIK29T5Fsu06qj3Yng");
        mockMvc.perform(get("/api/user/{idUser}/transaction/{idTransaction}", 12,12)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void userShowVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/user/show-all-voucher?page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void adminShowVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/admin/show-all-voucher?page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void userFilterVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/user/filter-voucher?merchantCategory=fnb&page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void adminFilterVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/admin/voucher/filterByStatus?filterBystatus=true&page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void userSearchVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/user/findByMerchantName-voucher?merchantName=k&page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void adminSearchVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/admin/findByMerchantName-voucher?merchantName=k&page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void userSortVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/user/sort-voucher?sortBy=k&page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void adminSortVoucher() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/admin/sort-voucher?sortBy=k&page=0")
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminVoucherDetail() throws Exception{

        JSONObject json = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/admin/voucher-detail-voucher/{idVoucher}", 1)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void adminUpdateVoucher() throws Exception{

        JSONObject json = new JSONObject();
        json.put("status", true);
        json.put("quota", 5);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(get("/api/admin/update-status-voucher/{idVoucher}/restock", 1)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void userLogout() throws Exception{

        JSONObject json = new JSONObject();
        json.put("virtualNumber", "90300878787877");
        json.put("amount", 5000);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNmI2MTg1ZC0wZmQ2LTQ5YWEtODFiZi0yNzhkZmE5YmRhZTgyMyIsImV4cCI6MTU5MzMzNjAzMSwiaWF0IjoxNTkyNzMxMjMxfQ.ha_gGqpdQY6tKWy5vG9XdZLCzN0_rr-59NURI4hHBc06kEGlksBmwDzUQWloVQOmf9CNOBcidyWkwakvxLaJSw");
        mockMvc.perform(post("/api/user/{idUser}/logout", 1)
                .headers(headers)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().is4xxClientError());

    }
}
