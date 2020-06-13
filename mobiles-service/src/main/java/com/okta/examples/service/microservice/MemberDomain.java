package com.okta.examples.service.microservice;

import com.okta.examples.model.request.EditProfileRequest;
import com.okta.examples.model.request.ForgotPasswordRequest;
import com.okta.examples.model.request.LoginRequest;
import com.okta.examples.model.request.RegisterRequest;
import com.okta.examples.adapter.template.Template;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MemberDomain {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${jwt.id.mobile}")
    private String token;

    @Autowired
    private Template template;

    private final String api = "http://localhost:8080";

    public ResponseEntity<?> register(RegisterRequest registerRequest, String path){
       return template.post(api+path, registerRequest);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest){
        return template.post(api+"/api/auth/login", loginRequest);
    }

    public ResponseEntity<?> requestOtp(JSONObject data){
        return template.post(api+"/api/auth/request-otp", data);
    }

    public ResponseEntity<?> matchOtp(String idUser, JSONObject data){
        return template.post(api+"/api/auth/"+idUser+"/match-otp", data);
    }

    public ResponseEntity<?> forgotPassword(String idUser, ForgotPasswordRequest forgotPasswordRequest){
        return template.post(api+"/api/auth/"+idUser+"/forgot-password", forgotPasswordRequest);
    }

    public ResponseEntity<?> getProfile(String idUser){
        return template.get(api+"/api/user/"+idUser);
    }

    public ResponseEntity<?> editProfile(String idUser, EditProfileRequest editProfileRequest){
        return template.put(api+"/api/user/"+idUser, editProfileRequest);
    }

    public ResponseEntity<?> logout(String idUser){
        return template.post(api+"/api/user/"+idUser+"/logout", null);
    }

}
