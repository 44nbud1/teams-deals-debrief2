package com.okta.example.validation;

import com.okta.examples.service.validation.UserValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserValidationTest {

    @Test
    public void editProfileTest(){

    }

    @Autowired
    UserValidation userValidation;

    @Test
    public void testMe(){
        assertEquals(true, userValidation.testMe());
    }
}
