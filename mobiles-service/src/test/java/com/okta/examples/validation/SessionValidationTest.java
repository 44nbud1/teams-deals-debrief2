package com.okta.examples.validation;

import com.okta.examples.service.validation.SessionValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SessionValidationTest {

    @Autowired
    SessionValidation sessionValidation;

    @Test
    public void checkSessionTest(){
        System.out.println("Check Session Validation Test");
        String path = "";
        String session = "";
        assertFalse(sessionValidation.checkSession(path, session));

        session = "da8ebe30-6b34-4b4b-95cb-f30bb3a8ea8337311";
        assertFalse(sessionValidation.checkSession(path, session));

        path = "/api/admin/";
        assertFalse(sessionValidation.checkSession(path, session));
    }
}
