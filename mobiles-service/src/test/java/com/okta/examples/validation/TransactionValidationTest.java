package com.okta.examples.validation;

import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.validation.TransactionValidation;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

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

        test.put("virtualNumber", "9080087897994929");
        test.put("amount", "10000.00");
        assertTrue(transactionValidation.payTopup(test, "").getStatusCode().is2xxSuccessful());

        test.put("virtualNumber", "900");
        assertEquals(DealsStatus.VIRTUAL_ACCOUNT_INVALID.getValue(), transactionValidation.payTopup(test, "/").getBody().get("status"));

        test.put("virtualNumber", "9080087897994929");
        test.put("amount", "aa");
        assertEquals(DealsStatus.AMOUNT_INVALID.getValue(), transactionValidation.payTopup(test, "/").getBody().get("status"));

    }
}
