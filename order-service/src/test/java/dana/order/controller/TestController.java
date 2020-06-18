package dana.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MobileController mobileController;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        assertThat(mobileController).isNotNull();
    }

    @Test
    void integratedTestTransactionHistory() throws Exception{

        JSONObject json = new JSONObject();

        mockMvc.perform(get("/api/user/{idUser}/transaction", 9)
                .contentType("application/json")
                //.param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

    }



}
