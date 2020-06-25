package com.MemberDomain.usecase.validation;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.model.request.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberValidationTest {

    @Autowired
    MemberValidation memberValidation;

    @Test
    public void registerTestValidation(){
        System.out.println("Registration Validation Test");
        RegisterRequest registerRequest = new RegisterRequest();

        assertFalse(memberValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

        registerRequest.setName("Aldie Adrian");
        registerRequest.setEmail("aldie@gmail.com");
        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("H0lmesHere!");
        registerRequest.setConfirmPassword("H0lmesHere!");
        assertTrue(memberValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

        registerRequest.setName("a");
        assertEquals(DealsStatus.NAME_INVALID.getValue(), memberValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setName("aldie");
        registerRequest.setEmail("aldiegmail.com");
        assertEquals(DealsStatus.EMAIL_INVALID.getValue(), memberValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setEmail("aldie@gmail.com");
        registerRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), memberValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("P@sswrd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), memberValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setPassword("P@ssw0rd");
        registerRequest.setConfirmPassword("P@sw0rd");
        assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), memberValidation.register(registerRequest, "/").getBody().get("status"));
    }

    @Test
    public void loginTestValidation(){
        System.out.println("Login Validation Test");
        LoginRequest loginRequest = new LoginRequest();

        assertFalse(memberValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("+6281287878787");
        loginRequest.setPassword("P@ssw0rd");
        assertTrue(memberValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), memberValidation.login(loginRequest, "/").getBody().get("status"));

        loginRequest.setPhoneNumber("+6281287878787");
        loginRequest.setPassword("P@sswrd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), memberValidation.login(loginRequest, "/").getBody().get("status"));
    }

    @Test
    public void requestOtpTestValidation(){
        System.out.println("Request Otp Validation Test");
        OtpRequest otpRequest = new OtpRequest();

        assertFalse(memberValidation.requestOtp(otpRequest, "/").getStatusCode().is2xxSuccessful());

        otpRequest.setPhoneNumber("+6281287878787");
        assertTrue(memberValidation.requestOtp(otpRequest, "/").getStatusCode().is2xxSuccessful());

        otpRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), memberValidation.requestOtp(otpRequest, "/").getBody().get("status"));
    }

    @Test
    public void matchOtpTestValidation(){
        System.out.println("Match Otp Validation Test");
        MatchOtpRequest matchOtpRequest = new MatchOtpRequest();

        assertFalse(memberValidation.matchOtp(matchOtpRequest, "/").getStatusCode().is2xxSuccessful());

        matchOtpRequest.setOtp("0000");
        assertTrue(memberValidation.matchOtp(matchOtpRequest, "/").getStatusCode().is2xxSuccessful());

        matchOtpRequest.setOtp("aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), memberValidation.matchOtp(matchOtpRequest, "/").getBody().get("status"));

    }

    @Test
    public void forgotPasswordValidation(){
        System.out.println("Forgot Password Validation Test");
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();

        assertFalse(memberValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

        forgotPasswordRequest.setNewPassword("P@ssw0rd");
        forgotPasswordRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(memberValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

        forgotPasswordRequest.setNewPassword("Passw0rd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), memberValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

        forgotPasswordRequest.setNewPassword("P@ssw0rd");
        forgotPasswordRequest.setConfirmPassword("Passw0rd");
        assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), memberValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

    }

    @Test
    public void editProfileTestValidation(){
        System.out.println("Edit Profile Validation Test");
        EditProfileRequest editProfileRequest = new EditProfileRequest();

        assertFalse(memberValidation.editProfile(editProfileRequest, "").getStatusCode().is2xxSuccessful());

        editProfileRequest.setName("kevin");
        editProfileRequest.setEmail("kevinard710@gmail.com");
        assertTrue(memberValidation.editProfile(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

        editProfileRequest.setName("k");
        assertEquals(DealsStatus.NAME_INVALID.getValue(), memberValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setName("kevin");
        editProfileRequest.setEmail("kevinard710gmail.com");
        assertEquals(DealsStatus.EMAIL_INVALID.getValue(), memberValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

    }

    @Test
    public void editPasswordTestValidation(){
        System.out.println("Edit Password Validation Test");
        EditProfileRequest editProfileRequest = new EditProfileRequest();

        editProfileRequest.setOldPassword("P@ssw0rd");
        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(memberValidation.editPassword(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

        editProfileRequest.setOldPassword("Pssw0rd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), memberValidation.editPassword(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setOldPassword("P@ssw0rd");
        editProfileRequest.setNewPassword("Pssw0rd");
        assertEquals(DealsStatus.NEW_PASSWORD_INVALID.getValue(), memberValidation.editPassword(editProfileRequest, "/").getBody().get("status"));

        editProfileRequest.setNewPassword("P@ssw0rd");
        editProfileRequest.setConfirmPassword("Pssw0rd");
        assertEquals(DealsStatus.NEW_PASSWORD_MISS_MATCH.getValue(), memberValidation.editPassword(editProfileRequest, "/").getBody().get("status"));

    }
}

