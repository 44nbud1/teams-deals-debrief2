package com.okta.examples.usecase;

import com.okta.examples.model.request.EditProfileRequest;
import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.usecase.UserService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void getProfileTest(){
        System.out.println("Get Profile Test");
        String path = "/api/user/19";
        String idUser = "19";

        assertTrue(userService.getProfile(idUser,  path).getStatusCode().is2xxSuccessful());

        idUser = "000";
        assertEquals(DealsStatus.USER_NOT_FOUND.getValue(), userService.getProfile(idUser, path).getBody().get("status"));

    }

    @Test
    public void editProfileTest(){
        System.out.println("Edit Profile Test");
        String path = "/api/user/19";
        String idUser = "19";
        EditProfileRequest editProfileRequest = new EditProfileRequest();

        editProfileRequest.setName("kevin");
        editProfileRequest.setEmail("kevinard710@gmail.com");
//        assertTrue(userService.editProfile(idUser,editProfileRequest,  path).getStatusCode().is2xxSuccessful());

        editProfileRequest.setName("kevin");
        editProfileRequest.setEmail("admin7asasn@gmail.co");
        assertEquals(DealsStatus.EMAIL_EXISTS.getValue(), userService.editProfile(idUser, editProfileRequest,path).getBody().get("status"));

        editProfileRequest.setName(null);editProfileRequest.setEmail(null);
        editProfileRequest.setOldPassword("P@ssw0rd");
        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(userService.editProfile(idUser, editProfileRequest, path).getStatusCode().is2xxSuccessful());

        editProfileRequest.setOldPassword("P@ssw0rdd");
        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("P@ssw0rd");
        assertEquals(DealsStatus.OLD_PASSWORD_NOT_MATCH.getValue(), userService.editProfile(idUser, editProfileRequest,path).getBody().get("status"));

        idUser = "000";
        assertEquals(DealsStatus.USER_NOT_FOUND.getValue(), userService.editProfile(idUser, editProfileRequest,path).getBody().get("status"));

    }

    @Test
    public void logoutTest(){
        System.out.println("Logout Test");
        String path = "/api/user/19";
        String idUser = "19";

        assertTrue(userService.logout(idUser,  path).getStatusCode().is2xxSuccessful());

    }
}
