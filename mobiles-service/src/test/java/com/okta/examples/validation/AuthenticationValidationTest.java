package com.okta.examples.validation;

import com.okta.examples.model.request.ForgotPasswordRequest;
import com.okta.examples.model.request.LoginRequest;
import com.okta.examples.model.request.RegisterRequest;
import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.validation.AuthenticationValidation;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthenticationValidationTest {

    @Autowired
    AuthenticationValidation authenticationValidation;

    @Test
    public void registerTest(){
        RegisterRequest registerRequest = new RegisterRequest();

        assertFalse(authenticationValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

        registerRequest.setName("kevin");
        registerRequest.setEmail("kevinard11@gmail.com");
        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("P@ssw0rd");
        registerRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(authenticationValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

        registerRequest.setName("k");
        assertEquals(DealsStatus.NAME_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setName("kevin");
        registerRequest.setEmail("kevinard11gmail.com");
        assertEquals(DealsStatus.EMAIL_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setName("kevin");
        registerRequest.setEmail("kevinard11@gmail.com");
        registerRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setName("kevin");
        registerRequest.setEmail("kevinard11@gmail.com");
        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("P@sswrd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

        registerRequest.setName("kevin");
        registerRequest.setEmail("kevinard11@gmail.com");
        registerRequest.setPhoneNumber("+6281287878787");
        registerRequest.setPassword("P@ssw0rd");
        registerRequest.setConfirmPassword("P@sw0rd");
        assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));
    }

    @Test
    public void loginTest(){
        LoginRequest loginRequest = new LoginRequest();

        assertFalse(authenticationValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("+6281287878787");
        loginRequest.setPassword("P@ssw0rd");
        assertTrue(authenticationValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), authenticationValidation.login(loginRequest, "/").getBody().get("status"));

        loginRequest.setPhoneNumber("+6281287878787");
        loginRequest.setPassword("P@sswrd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), authenticationValidation.login(loginRequest, "/").getBody().get("status"));
    }
    
    @Test
    public void requestOtpTest(){
        JSONObject test = new JSONObject();
        
        assertFalse(authenticationValidation.requestOtp(test, "/").getStatusCode().is2xxSuccessful());
        
        test.put("phoneNumber", "+6281287878787");
        assertTrue(authenticationValidation.requestOtp(test, "/").getStatusCode().is2xxSuccessful());

        test.put("phoneNumber", "+6280287878787");
        assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), authenticationValidation.requestOtp(test, "/").getBody().get("status"));

    }

    @Test
    public void matchOtpTest(){
        JSONObject test = new JSONObject();

        assertFalse(authenticationValidation.matchOtp(test, "/").getStatusCode().is2xxSuccessful());

        test.put("otp", "0000");
        assertTrue(authenticationValidation.matchOtp(test, "/").getStatusCode().is2xxSuccessful());

        test.put("otp", "aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), authenticationValidation.matchOtp(test, "/").getBody().get("status"));

    }

    @Test
    public void forgotPassword(){
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();

        assertFalse(authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

        forgotPasswordRequest.setNewPassword("P@ssw0rd");
        forgotPasswordRequest.setConfirmPassword("P@ssw0rd");
        assertTrue(authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

        forgotPasswordRequest.setNewPassword("Passw0rd");
        assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

        forgotPasswordRequest.setNewPassword("P@ssw0rd");
        forgotPasswordRequest.setConfirmPassword("Passw0rd");
        assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

    }
}
