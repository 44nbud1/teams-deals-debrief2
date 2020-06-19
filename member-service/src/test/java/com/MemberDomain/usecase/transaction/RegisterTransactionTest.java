package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.model.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class RegisterTransactionTest {

    @Autowired
    RegisterTransaction registerTransaction;

    @Test
    public void registerTest() {
        System.out.println("Registration Test");
        String path = "/api/auth/register";
        assertFalse(registerTransaction.createAccount(null, path).getStatusCode().is2xxSuccessful());

        RegisterRequest registerRequest = new RegisterRequest();

        assertFalse(registerTransaction.createAccount(registerRequest, path).getStatusCode().is2xxSuccessful());

        registerRequest.setName("Aldie Adrian");
        registerRequest.setEmail("aldieadrian@gmail.com");
        registerRequest.setPhoneNumber("082118941234");
        registerRequest.setPassword("H0lmesHere!");
        registerRequest.setConfirmPassword("H0lmesHere!");
        //assertTrue(registerTransaction.createAccount(registerRequest, path).getStatusCode().is2xxSuccessful());

        registerRequest.setPassword("H0lmesHere!");
        registerRequest.setConfirmPassword("H0lmesHere!");
        assertEquals(DealsStatus.EMAIL_EXISTS.getValue(), registerTransaction.createAccount(registerRequest, path).getBody().get("status"));

        registerRequest.setEmail("aldie17@gmail.com");
        assertEquals(DealsStatus.PHONE_NUMBER_EXISTS.getValue(), registerTransaction.createAccount(registerRequest, path).getBody().get("status"));
    }
}
