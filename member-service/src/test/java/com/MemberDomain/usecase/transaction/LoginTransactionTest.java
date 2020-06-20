package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.model.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginTransactionTest {

    @Autowired
    LoginTransaction loginTransaction;

    @Test
    public void loginTest(){
        System.out.println("Login Test");
        String path = "/api/auth/login";

        assertFalse(loginTransaction.login(null, path).getStatusCode().is2xxSuccessful());

        LoginRequest loginRequest = new LoginRequest();
        assertFalse(loginTransaction.login(loginRequest, path).getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("082118941234");
        loginRequest.setPassword("H0lmesHere!");
        assertTrue(loginTransaction.login(loginRequest, path).getStatusCode().is2xxSuccessful());

        loginRequest.setPassword("W4ts0nHere!");
        assertEquals(DealsStatus.DATA_NOT_MATCH.getValue(), loginTransaction.login(loginRequest, path).getBody().get("status"));

    }
}
