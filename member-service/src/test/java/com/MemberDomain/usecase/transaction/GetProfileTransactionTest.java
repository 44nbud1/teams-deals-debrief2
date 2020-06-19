package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.model.request.OtpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GetProfileTransactionTest {

    @Autowired
    GetProfileTransaction getProfileTransaction;

    @Test
    public void getProfileTest(){
        System.out.println("Get Profile Test");
        String path = "/api/user/31";
        String idUser = "31";

        assertTrue(getProfileTransaction.getProfile(idUser,  path).getStatusCode().is2xxSuccessful());

        idUser = "000";
        assertEquals(DealsStatus.USER_NOT_FOUND.getValue(), getProfileTransaction.getProfile(idUser, path).getBody().get("status"));

    }
}
