package com.okta.examples.validation;

import com.okta.examples.model.request.EditProfileRequest;
import com.okta.examples.model.status.DealsStatus;
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
    public void editProfileTestValidation(){
        System.out.println("Edit Profile Validation Test");
        EditProfileRequest editProfileRequest = new EditProfileRequest();

        assertFalse(userValidation.editProfile(editProfileRequest, "").getStatusCode().is2xxSuccessful());

        editProfileRequest.setName("kevin");
        editProfileRequest.setEmail("kevinard710@gmail.com");
        assertTrue(userValidation.editProfile(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

        editProfileRequest.setName("k");
        assertEquals(DealsStatus.NAME_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setName("kevin");
        editProfileRequest.setEmail("kevinard710gmail.com");
        assertEquals(DealsStatus.EMAIL_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setName(null);editProfileRequest.setEmail(null);

        editProfileRequest.setOldPassword("P@ssw0rd");
        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(userValidation.editProfile(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

        editProfileRequest.setOldPassword("Pssw0rd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setOldPassword("P@ssw0rd");
        editProfileRequest.setNewPassword("Pssw0rd");
        assertEquals(DealsStatus.NEW_PASSWORD_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("Pssw0rd");
        assertEquals(DealsStatus.NEW_PASSWORD_MISS_MATCH.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

    }
}
