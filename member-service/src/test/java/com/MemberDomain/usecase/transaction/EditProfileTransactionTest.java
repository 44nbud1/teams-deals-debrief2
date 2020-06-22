package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.model.request.EditProfileRequest;;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EditProfileTransactionTest {

    @Autowired
    EditProfileTransaction editProfileTransaction;

    @Test
    public void editProfileTest(){
        System.out.println("Edit Profile Test");
        String path = "/api/user/31";
        String idUser = "31";
        EditProfileRequest editProfileRequest = new EditProfileRequest();

        editProfileRequest.setName("Aldie");
        editProfileRequest.setEmail("aldieadrian@gmail.com");
        //assertTrue(editProfileTransaction.editProfile(idUser,editProfileRequest,  path).getStatusCode().is2xxSuccessful());

        editProfileRequest.setName("Aldie");
        editProfileRequest.setEmail("aldieadrian@gmail.com");
        //assertEquals(DealsStatus.EMAIL_EXISTS.getValue(), editProfileTransaction.editProfile(idUser, editProfileRequest,path).getBody().get("status"));

        editProfileRequest.setName(null);editProfileRequest.setEmail(null);
        editProfileRequest.setOldPassword("H0lmesHere!");
        editProfileRequest.setNewPassword("H0lmesH3re!");
        editProfileRequest.setConfirmPassword("H0lmesH3re!");
        //assertTrue(editProfileTransaction.editProfile(idUser, editProfileRequest, path).getStatusCode().is2xxSuccessful());

        editProfileRequest.setOldPassword("H0lmesH3re!");
        editProfileRequest.setNewPassword("H0lmesH3re!a");
        editProfileRequest.setConfirmPassword("H0lmesH3re!a");
        assertEquals(DealsStatus.OLD_PASSWORD_NOT_MATCH.getValue(), editProfileTransaction.editProfile(idUser, editProfileRequest,path).getBody().get("status"));

        idUser = "000";
        assertEquals(DealsStatus.USER_NOT_FOUND.getValue(), editProfileTransaction.editProfile(idUser, editProfileRequest,path).getBody().get("status"));

    }
}
