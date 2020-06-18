package com.okta.examples.usecase;

import com.okta.examples.model.request.ForgotPasswordRequest;
import com.okta.examples.model.request.LoginRequest;
import com.okta.examples.model.request.RegisterRequest;
import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.usecase.AuthenticationService;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @Test
    public void registerTest(){
        System.out.println("Registration Test");
        assertFalse(authenticationService.register(null, "/api/auth/register").getStatusCode().is2xxSuccessful());

        RegisterRequest registerRequest = new RegisterRequest();

        assertFalse(authenticationService.register(registerRequest, "/api/auth/register").getStatusCode().is2xxSuccessful());

        registerRequest.setName("kevin");
        registerRequest.setEmail("kevinard11@gmail.com");
        registerRequest.setPhoneNumber("+6287878787878");
        registerRequest.setPassword("P@ssw0rd");
        registerRequest.setConfirmPassword("P@ssw0rd");
//        assertTrue(authenticationService.register(registerRequest, "/api/auth/register").getStatusCode().is2xxSuccessful());

        assertEquals(DealsStatus.EMAIL_EXISTS.getValue(), authenticationService.register(registerRequest, "/api/auth/register").getBody().get("status"));

        registerRequest.setEmail("kevissrd11@gmail.com");
        assertEquals(DealsStatus.PHONE_NUMBER_EXISTS.getValue(), authenticationService.register(registerRequest, "/api/auth/register").getBody().get("status"));

    }

    @Test
    public void loginTest(){
        System.out.println("Login Test");
        String request = "null";
        String path = "/api/auth/login";
        assertFalse(authenticationService.login(null, path, request).getStatusCode().is2xxSuccessful());

        LoginRequest loginRequest = new LoginRequest();
        assertFalse(authenticationService.login(loginRequest, path, request).getStatusCode().is2xxSuccessful());

        loginRequest.setPhoneNumber("+6287878787878");
        loginRequest.setPassword("P@ssw0rd");
        assertTrue(authenticationService.login(loginRequest, path, request).getStatusCode().is2xxSuccessful());

        assertEquals(DealsStatus.LOGIN_ANOTHER_DEVICE.getValue(), authenticationService.login(loginRequest, path, request).getBody().get("status"));

        loginRequest.setPassword("P@sssw112d");
        assertEquals(DealsStatus.DATA_NOT_MATCH.getValue(), authenticationService.login(loginRequest, path, request).getBody().get("status"));

    }

    @Test
    public void requestOtp(){

        System.out.println("Request Otp Test");
        String path = "/api/auth/request-otp";
        JSONObject test = new JSONObject();

        assertFalse(authenticationService.requestOtp(test, path).getStatusCode().is2xxSuccessful());

        test.put("phoneNumber", "+6287819411111");
        assertFalse(authenticationService.requestOtp(test, path).getStatusCode().is2xxSuccessful());

        test.put("phoneNumber", "+6281200878787");
        assertEquals(DealsStatus.PHONE_NUMBER_NOT_EXISTS.getValue(), authenticationService.requestOtp(test, path).getBody().get("status"));

    }

    @Test
    public void matchOtp(){
        System.out.println("Match Otp Test");
        String path = "/api/auth/19/match-otp";
        String idUser = "19";
        JSONObject test = new JSONObject();

        assertFalse(authenticationService.matchOtp(idUser,test, path).getStatusCode().is2xxSuccessful());

        test.put("otp", "0000");
        assertFalse(authenticationService.matchOtp(idUser, test, path).getStatusCode().is2xxSuccessful());

        assertEquals(DealsStatus.ALREADY_LOGIN.getValue(), authenticationService.matchOtp(idUser, test, path).getBody().get("status"));

        test.put("phoneNumber", "+6287819411111");
        assertFalse(authenticationService.requestOtp(test, "/api/auth/request-otp").getStatusCode().is2xxSuccessful());
        test.put("otp", "0012");
        assertEquals(DealsStatus.ALREADY_LOGIN.getValue(), authenticationService.matchOtp(idUser, test, path).getBody().get("status"));

    }

    @Test
    public void forgotPassword(){
        System.out.println("Forgot Password Test");
        String path = "/api/auth/19/match-otp";
        String idUser = "19";
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        JSONObject test = new JSONObject();
        assertFalse(authenticationService.forgotPassword(idUser,forgotPasswordRequest, path).getStatusCode().is2xxSuccessful());

        test.put("otp", "0000");
        assertFalse(authenticationService.matchOtp(idUser, test, path).getStatusCode().is2xxSuccessful());
        forgotPasswordRequest.setPassword("P@ssw0rd");
        forgotPasswordRequest.setConfirmPassword("P@ssw0rd");
        assertFalse(authenticationService.forgotPassword(idUser, forgotPasswordRequest, path).getStatusCode().is2xxSuccessful());

        test.put("phoneNumber", "+6287819411111");
        assertFalse(authenticationService.requestOtp(test, "/api/auth/request-otp").getStatusCode().is2xxSuccessful());
        test.put("otp", "0000");
        assertFalse( authenticationService.matchOtp(idUser, test, path).getStatusCode().is2xxSuccessful());
        forgotPasswordRequest.setPassword("P@ssw0rddd");
        forgotPasswordRequest.setConfirmPassword("P@ssw0rd");
        assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), authenticationService.forgotPassword(idUser, forgotPasswordRequest, path).getBody().get("status"));

    }

}