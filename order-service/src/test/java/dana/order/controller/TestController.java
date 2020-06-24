package dana.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dana.order.adapter.encoder.TokenGeneratorUUID;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Autowired
    TokenGeneratorUUID tokenGeneratorUUID;

    @Test
    void contextLoads() throws Exception {
        assertThat(mobileController).isNotNull();
    }

    @Test
    void integratedTestTransactionHistory() throws Exception{

        JSONObject json = new JSONObject();

        mockMvc.perform(get("/api/user/{idUser}/transaction", 9)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/{idUser}/transaction", 9)
                .contentType("application/json")
                .param("category", "NOTME")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/user/{idUser}/transaction", 9)
                .contentType("application/json")
                .param("category", "IN-PROGRESS")
                .param("filter-start-date", "2020-05-12")
                .param("filter-end-date", "2020-05-15")
                .param("page", "0")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/{idUser}/transaction", 9)
                .contentType("application/json")
                .param("category", "IN-PROGRESS")
                .param("filter-start-date", "2020-05-12df")
                .param("filter-end-date", "2020-05-15")
                .param("page", "-2")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/user/{idUser}/transaction", 9)
                .contentType("application/json")
                .param("category", "IN-PROGRESS")
                .param("filter-start-date", "2020-05-20")
                .param("filter-end-date", "2020-05-15")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void integratedTestDetailedTransactionHistory() throws Exception{

        mockMvc.perform(get("/api/user/{idUser}/transaction/{idTransaction}", 9, 1)
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/{idUser}/transaction/{idTransaction}", 9, 2)
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/{idUser}/transaction/{idTransaction}", 9, -2)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/user/{idUser}/transaction/{idTransaction}", 9, 13)
                .contentType("application/json"))
                .andExpect(status().isNotFound());

    }

    @Test
    void integratedTestPlaceOrder() throws Exception{
        JSONObject json = new JSONObject();
        json.put("idVoucher", 0);

        mockMvc.perform(post("/api/user/{idUser}/transaction/voucher", 9)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/api/user/{idUser}/transaction/voucher", -2)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isNotFound());

        String key = tokenGeneratorUUID.generateToken();
        json.put("idVoucher",1);
        mockMvc.perform(post("/api/user/{idUser}/transaction/voucher?key="+key, 9)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isCreated());
    }

    @Test
    void integratedTestPayOrder() throws Exception{

        JSONObject json = new JSONObject();
        json.put("idTransaction", 1);

        mockMvc.perform(put("/api/user/{idUser}/transaction/voucher", 9)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isNotAcceptable());

    }

    @Test
    void integratedTestTOPUP() throws Exception{

        JSONObject json = new JSONObject();
        json.put("virtualNumber", "9130081222371122");
        json.put("amount", 50000);

        mockMvc.perform(post("/api/user/{idUser}/transaction/topup", 9)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isNotAcceptable());

        json.put("virtualNumber", "9030081222371122");
        json.put("amount", 15000);
        mockMvc.perform(post("/api/user/{idUser}/transaction/topup", 9)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isCreated());
    }

    @Test
    void refund() throws Exception{
        integratedPlaceOrderWithRefund();
        integratedPlaceOrderWithRefund();
    }

    void integratedPlaceOrderWithRefund() throws Exception{
        JSONObject voucher = new JSONObject();
        voucher.put("idVoucher", 1);

        String key = tokenGeneratorUUID.generateToken();
        MvcResult result = mockMvc.perform(post("/api/user/{idUser}/transaction/voucher?key="+key, 18)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(voucher)))
                .andExpect(status().isCreated())
                .andReturn();

        JSONParser parser = new JSONParser();

        JSONObject response = (JSONObject) parser.parse(result.getResponse().getContentAsString());

        JSONObject transaction = (JSONObject) parser.parse(""+response.get("data"));

        mockMvc.perform(put("/api/user/{idUser}/transaction/voucher", 18)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk());
        Thread.sleep(3000);
    }

}
