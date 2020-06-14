package com.MemberDomain.controller;

import com.MemberDomain.usecase.port.BalanceMapper;
import com.MemberDomain.usecase.port.OtpMapper;
import com.MemberDomain.usecase.port.UserMapper;
import com.MemberDomain.model.request.*;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.transaction.LoginTransaction;
import com.MemberDomain.usecase.transaction.MatchOtpTransaction;
import com.MemberDomain.usecase.transaction.RegisterTransaction;
import com.MemberDomain.usecase.transaction.RequestOtpTransaction;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private RegisterTransaction registerTransaction;

    @Autowired
    private LoginTransaction loginTransaction;

    @Autowired
    private RequestOtpTransaction requestOtpTransaction;

    @Autowired
    private MatchOtpTransaction matchOtpTransaction;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private OtpMapper otpMapper;

    //get all user
    @GetMapping("/auth/user/all")
    public ResponseEntity<?> getAllUser(){
        List<UserDataResponse> allUser=  userMapper.getAll();
        if (allUser.isEmpty()) {
            JSONObject jsonObject = new JSONObject();
            JSONObject empty = new JSONObject();
            jsonObject.put("data", empty);
            jsonObject.put("message", "User not found");
            jsonObject.put("status", "404");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", allUser);
            jsonObject.put("message", "All user data has successfully sent");
            jsonObject.put("status", "200");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
    }

    //insert user
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        return registerTransaction.createAccount(registerRequest, request.getServletPath());
    }

    // login user
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return loginTransaction.login(loginRequest, request.getServletPath());
    }

    // request OTP
    @PostMapping("/auth/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody OtpRequest otpRequest, HttpServletRequest request) {
        return requestOtpTransaction.requestOtp(otpRequest, request.getServletPath());
    }

    // match OTP
    @PostMapping("/auth/{idUser}/match-otp")
    public ResponseEntity<?> matchOtp(@PathVariable String idUser, @RequestBody MatchOtpRequest matchOtpRequest, HttpServletRequest request) { ;
        return matchOtpTransaction.matchOtp(idUser, matchOtpRequest, request.getServletPath());
    }

    // forgot password
    @PostMapping("/auth/{idUser}/forgot-password")
    public ResponseEntity<?> forgotPassword(@PathVariable String idUser, @RequestBody ForgotPasswordRequest forgot){
        if(!forgot.getPassword().equals(forgot.getConfirmPassword())){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Password is missed match.");
            jsonObject.put("status", "404");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
        ProfileResponse selectedUser = userMapper.getUserProfile(idUser);
        if (selectedUser == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "User is not found");
            jsonObject.put("status", "404");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
        else{
            userMapper.changePassword(forgot.getPassword(), idUser);
            JSONObject jsonObject = new JSONObject();
            JSONObject empty = new JSONObject();
            jsonObject.put("data", empty);
            jsonObject.put("message", "You need to request new OTP.");
            jsonObject.put("status", "200");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
    }

    // get user profile
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> profile(@PathVariable String idUser){
        ProfileResponse profile = userMapper.getUserProfile(idUser);
        if (profile == null) {
            JSONObject jsonObject = new JSONObject();
            JSONObject empty = new JSONObject();
            jsonObject.put("message", "User not found");
            jsonObject.put("status", "404");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
        else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", profile);
            jsonObject.put("message", "User profile has successfully sent");
            jsonObject.put("status", "200");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
    }
}