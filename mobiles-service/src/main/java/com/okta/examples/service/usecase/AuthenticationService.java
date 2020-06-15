package com.okta.examples.service.usecase;

import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.adapter.parser.Parser;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import com.okta.examples.model.request.ForgotPasswordRequest;
import com.okta.examples.model.request.LoginRequest;
import com.okta.examples.model.request.RegisterRequest;
import com.okta.examples.service.microservice.MemberDomain;
import com.okta.examples.service.validation.AuthenticationValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    MemberDomain member;

    @Autowired
    SessionService sessionService;

    @Autowired
    AuthenticationValidation validate;

    public ResponseEntity<?> register(RegisterRequest registerRequest, String path) {

        //Register validation
        System.out.println("Register Validation. " +new Date());
        ResponseEntity<?> check = validate.register(registerRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

//        if (registerRequest.getPhoneNumber().startsWith("0")){
//            registerRequest.setPhoneNumber("+62"+registerRequest.getPhoneNumber().substring(1));
//        }
//        registerRequest.setPassword(encryptPassword(registerRequest.getPassword()));
        //Register validation in member domain
        System.out.println("Register. Send data to member domain : "+ Parser.toJsonString(registerRequest));
        ResponseEntity<?> fromMember = member.register(registerRequest, path);
        System.out.println("Register. Receive data from member domain :"+ fromMember.getBody().toString());

        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
        String message = ""+ jsonMember.get("message");
        String status = ""+jsonMember.get("status");

        //Check response
        if (!fromMember.getStatusCode().is2xxSuccessful()){
           return ResponseFailed.wrapResponseFailed(message, status, fromMember.getStatusCode(), path);
        }

        //Create user
        JSONObject user = (JSONObject) jsonMember.get("data");
//        String idUser = ""+user.get("idUser");
//
//        //Create and start session
//        System.out.println("Start new session for id : " + idUser);
//        String idSession = UUID.randomUUID().toString();
//        sessionService.startSession(idUser, idSession);

        //Wrap response
        JSONObject result = new JSONObject();
//        result.put("token", idSession);
        result.put("user", user);

        return ResponseSuccess.wrapResponse(result, DealsStatus.REGISTRATION_SUCCESS, path);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest, String path){

        //Login validation
        System.out.println("Login Validation. " +new Date());
        ResponseEntity<?> check = validate.login(loginRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

//        if (loginRequest.getPhoneNumber().startsWith("0")){
//            loginRequest.setPhoneNumber("+62"+loginRequest.getPhoneNumber().substring(1));
//        }

        //Login validation in member domain
        System.out.println("Login. Send data to member domain : "+ Parser.toJsonString(loginRequest));
        ResponseEntity<?> fromMember = member.login(loginRequest);
        System.out.println("Login. Receive data from member domain :"+ fromMember.getBody().toString());

        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
        String message = ""+ jsonMember.get("message");
        String status = ""+jsonMember.get("status");

        //Check response
        if (!fromMember.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromMember.getStatusCode(), path);
        }

        //Create user
        JSONObject user = (JSONObject) jsonMember.get("data");
        String idUser = ""+user.get("idUser");
        String idSession= UUID.randomUUID().toString()+idUser;

        //Check user session
        System.out.println("Check if id : "+ idUser+" already have session");
        if(sessionService.checkSession(idUser) != null){

            System.out.println("Session found. Destroy old session for id : "+idUser);
            sessionService.destroySession(idUser);
            System.out.println("Start new session for id : " + idUser);

            //Create and start session
            sessionService.startSession(idUser, idSession);

            //Wrap response
            JSONObject result = new JSONObject();
            result.put("token", idSession);
            result.put("user", user);

            return ResponseSuccess.wrapResponse(result, DealsStatus.LOGIN_ANOTHER_DEVICE, path);
        }else {

            System.out.println("Session not found for id : "+ idUser);
            System.out.println("Start new session for id : " + idUser);

            //Create and start session
            sessionService.startSession(idUser, idSession);

            //Wrap response
            JSONObject result = new JSONObject();
            result.put("token", idSession);
            result.put("user", user);

            return ResponseSuccess.wrapResponse(result, DealsStatus.LOGIN_SUCCESS, path);
        }
    }

    public ResponseEntity<?> requestOtp(JSONObject data, String path){

        //Request otp validation
        ResponseEntity<?> check = validate.requestOtp(data, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }
        String phoneNumber = (""+data.get("phoneNumber"));
        if (phoneNumber.startsWith("0")){
            phoneNumber = "+62"+phoneNumber.substring(1);
        }
        data.put("phoneNumber", phoneNumber);
        //Request otp validation in member domain
        System.out.println("Request OTP. Send data to member domain : "+ Parser.toJsonString(data));
        ResponseEntity<?> fromMember = member.requestOtp(data);
        System.out.println("Request OTP. Receive data from member domain :"+ fromMember.getBody().toString());

        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
        String message = ""+ jsonMember.get("message");
        String status = ""+jsonMember.get("status");

        //Check response
        if (!fromMember.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromMember.getStatusCode(), path);
        }

        JSONObject user = (JSONObject) jsonMember.get("data");
        String idUser = ""+user.get("idUser");

        //Wrap response
        return ResponseSuccess.wrapResponse(user, DealsStatus.REQUEST_OTP, path);
    }

    public ResponseEntity<?> matchOtp(String idUser, JSONObject data, String path){

        // Match otp validation
        ResponseEntity<?> check = validate.matchOtp(data, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        //Match otp validation in member domain
        System.out.println("Match OTP. Send data to member domain : "+ Parser.toJsonString(data));
        ResponseEntity<?> fromMember = member.matchOtp(idUser, data);
        System.out.println("Match OTP. Receive data from member domain :"+ fromMember.getBody().toString());

        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
        String message = ""+ jsonMember.get("message");
        String status = ""+jsonMember.get("status");

        //Check response
        if (!fromMember.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromMember.getStatusCode(), path);
        }

        //Wrap response
        return ResponseSuccess.wrapResponse(null, DealsStatus.OTP_MATCH, path);
//        return ResponseSuccess.wrap200(null, "OTP Match.",
//                "/api/auth/"+idUser+"/match-otp");
    }

    public ResponseEntity<?> forgotPassword(String idUser, ForgotPasswordRequest forgotPasswordRequest, String path){

        //Forgot password validation
        ResponseEntity<?> check = validate.forgotPassword(forgotPasswordRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }
        JSONObject data = new JSONObject();
        data.put("password", forgotPasswordRequest.getNewPassword());
        data.put("newPassword", forgotPasswordRequest.getNewPassword());
        data.put("confirmPassword", forgotPasswordRequest.getConfirmPassword());
        //Forgot password validation in member domain
        System.out.println("Forgot Password. Send data to member domain : "+ Parser.toJsonString(forgotPasswordRequest));
        ResponseEntity<?> fromMember = member.forgotPassword(idUser, data);
        System.out.println("Forgot Password. Receive data from member domain :"+ fromMember.getBody().toString());

        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
        String message = ""+ jsonMember.get("message");
        String status = ""+jsonMember.get("status");

        //Check response
        if (!fromMember.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromMember.getStatusCode(), path);
        }

        //Wrap response
        return ResponseSuccess.wrapResponse(null, DealsStatus.FORGOT_PASSWORD, path);
    }

    public ResponseEntity<JSONObject> test(JSONObject data, String path){
        if (data == null){
            return ResponseFailed.wrapResponseFailed( "You are not authorized",
                                                    "201", HttpStatus.UNAUTHORIZED, path);
        }
        return ResponseSuccess.wrapResponseSuccess(path, "Success", 100, HttpStatus.OK, "/sda");
    }

}
