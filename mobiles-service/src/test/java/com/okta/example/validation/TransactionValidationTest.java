package com.okta.example.validation;

import com.okta.examples.service.validation.TransactionValidation;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class TransactionValidationTest {

    @Autowired
    TransactionValidation transactionValidation;

    @Test
    public void createOrderTest(){
        JSONObject test = new JSONObject();

        assertFalse(transactionValidation.createOrder(test, "").getStatusCode().is2xxSuccessful());

        test.put("idVoucher", 1);
        assertTrue(transactionValidation.createOrder(test, "").getStatusCode().is2xxSuccessful());

    }

    @Test
    public void payOrderTest(){
        JSONObject test = new JSONObject();

        assertFalse(transactionValidation.payOrder(test, "").getStatusCode().is2xxSuccessful());

        test.put("idTransaction", 1);
        assertTrue(transactionValidation.payOrder(test, "").getStatusCode().is2xxSuccessful());
    }

    @Test
    public void payTopupTest(){
        JSONObject test = new JSONObject();

        assertFalse(transactionValidation.payTopup(test, "").getStatusCode().is2xxSuccessful());

        test.put("virtualNumber", "90800808088080");
        test.put("amount", 76000);
        assertTrue(transactionValidation.payOrder(test, "").getStatusCode().is2xxSuccessful());

        test.put("virtualNumber", "900");
        test.put("amount", "76000");
        assertFalse(transactionValidation.payOrder(test, "").getStatusCode().is2xxSuccessful());

    }
}
