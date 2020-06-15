package com.okta.examples.validation;

import com.okta.examples.model.request.EditProfileRequest;
import com.okta.examples.service.validation.UserValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserValidationTest {

    @Autowired
    UserValidation userValidation;

    @Test
    public void editProfileTest(){
        EditProfileRequest editProfileRequest = new EditProfileRequest();

        assertFalse(userValidation.editProfile(editProfileRequest, "").getStatusCode().is2xxSuccessful());

        editProfileRequest.setEmail("kevinard710@gmail.com");
        editProfileRequest.setName("kevin");
        assertTrue(userValidation.editProfile(editProfileRequest, "").getStatusCode().is2xxSuccessful());

        editProfileRequest.setName("k");
    }
}
