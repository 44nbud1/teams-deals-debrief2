package com.MemberDomain.usecase.validation;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.model.request.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserValidationTest {

    @Autowired
    UserValidation userValidation;

    @Test
    public void registerTestValidation(){
        System.out.println("Registration Validation Test");
        RegisterRequest registerRequest = new RegisterRequest();

        assertFalse(userValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

        registerRequest.setName("Aldie Adrian");
        registerRequest.setEmail("aldie@gmail.com");
        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("H0lmesHere!");
        registerRequest.setConfirmPassword("H0lmesHere!");
        assertTrue(userValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

        registerRequest.setName("a");
        assertEquals(DealsStatus.NAME_INVALID.getValue(), userValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setName("aldie");
        registerRequest.setEmail("aldiegmail.com");
        assertEquals(DealsStatus.EMAIL_INVALID.getValue(), userValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setEmail("aldie@gmail.com");
        registerRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), userValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("P@sswrd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), userValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setPassword("P@ssw0rd");
        registerRequest.setConfirmPassword("P@sw0rd");
        assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), userValidation.register(registerRequest, "/").getBody().get("status"));
    }

    @Test
    public void loginTestValidation(){
        System.out.println("Login Validation Test");
        LoginRequest loginRequest = new LoginRequest();

        assertFalse(userValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("+6281287878787");
        loginRequest.setPassword("P@ssw0rd");
        assertTrue(userValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), userValidation.login(loginRequest, "/").getBody().get("status"));

        loginRequest.setPhoneNumber("+6281287878787");
        loginRequest.setPassword("P@sswrd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), userValidation.login(loginRequest, "/").getBody().get("status"));
    }

    @Test
    public void requestOtpTestValidation(){
        System.out.println("Request Otp Validation Test");
        OtpRequest otpRequest = new OtpRequest();

        assertFalse(userValidation.requestOtp(otpRequest, "/").getStatusCode().is2xxSuccessful());

        otpRequest.setPhoneNumber("+6281287878787");
        assertTrue(userValidation.requestOtp(otpRequest, "/").getStatusCode().is2xxSuccessful());

        otpRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), userValidation.requestOtp(otpRequest, "/").getBody().get("status"));
    }

    @Test
    public void matchOtpTestValidation(){
        System.out.println("Match Otp Validation Test");
        MatchOtpRequest matchOtpRequest = new MatchOtpRequest();

        assertFalse(userValidation.matchOtp(matchOtpRequest, "/").getStatusCode().is2xxSuccessful());

        matchOtpRequest.setOtp("0000");
        assertTrue(userValidation.matchOtp(matchOtpRequest, "/").getStatusCode().is2xxSuccessful());

        matchOtpRequest.setOtp("aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), userValidation.matchOtp(matchOtpRequest, "/").getBody().get("status"));

    }

    @Test
    public void forgotPasswordValidation(){
        System.out.println("Forgot Password Validation Test");
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();

        assertFalse(userValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

        forgotPasswordRequest.setNewPassword("P@ssw0rd");
        forgotPasswordRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(userValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

        forgotPasswordRequest.setNewPassword("Passw0rd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), userValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

        forgotPasswordRequest.setNewPassword("P@ssw0rd");
        forgotPasswordRequest.setConfirmPassword("Passw0rd");
        assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), userValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

    }

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

    }

    @Test
    public void editPasswordTestValidation(){
        System.out.println("Edit Password Validation Test");
        EditProfileRequest editProfileRequest = new EditProfileRequest();

        editProfileRequest.setOldPassword("P@ssw0rd");
        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(userValidation.editPassword(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

        editProfileRequest.setOldPassword("Pssw0rd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), userValidation.editPassword(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setOldPassword("P@ssw0rd");
        editProfileRequest.setNewPassword("Pssw0rd");
        assertEquals(DealsStatus.NEW_PASSWORD_INVALID.getValue(), userValidation.editPassword(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("Pssw0rd");
        assertEquals(DealsStatus.NEW_PASSWORD_MISS_MATCH.getValue(), userValidation.editPassword(editProfileRequest, "/").getBody().get("status"));

    }
}

