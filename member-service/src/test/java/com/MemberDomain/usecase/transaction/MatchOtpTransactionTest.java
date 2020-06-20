package com.MemberDomain.usecase.transaction;

import com.MemberDomain.model.request.MatchOtpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MatchOtpTransactionTest {

    @Autowired
    MatchOtpTransaction matchOtpTransaction;

    @Test
    public void matchOtp(){
        System.out.println("Match Otp Test");
        String path = "/api/auth/33/match-otp";
        String idUser = "33";

        MatchOtpRequest matchOtpRequest = new MatchOtpRequest();

        assertFalse(matchOtpTransaction.matchOtp(idUser, matchOtpRequest, path).getStatusCode().is2xxSuccessful());

        matchOtpRequest.setOtp("0000");
        assertTrue(matchOtpTransaction.matchOtp(idUser, matchOtpRequest, path).getStatusCode().is2xxSuccessful());
    }
}
