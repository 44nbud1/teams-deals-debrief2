package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.model.request.OtpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RequestOtpTransactionTest {

    @Autowired
    RequestOtpTransaction requestOtpTransaction;

    @Test
    public void requestOtp(){

        System.out.println("Request Otp Test");
        String path = "/api/auth/request-otp";

        OtpRequest otpRequest = new OtpRequest();
        assertFalse(requestOtpTransaction.requestOtp(otpRequest, path).getStatusCode().is2xxSuccessful());

        otpRequest.setPhoneNumber("082118941234");
        assertTrue(requestOtpTransaction.requestOtp(otpRequest, path).getStatusCode().is2xxSuccessful());

        otpRequest.setPhoneNumber("082118941231");
        assertEquals(DealsStatus.PHONE_NUMBER_NOT_EXISTS.getValue(), requestOtpTransaction.requestOtp(otpRequest, path).getBody().get("status"));

    }
}
