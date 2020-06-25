package com.MemberDomain.controller;

import com.MemberDomain.usecase.port.BalanceMapper;
import com.MemberDomain.usecase.port.OtpMapper;
import com.MemberDomain.usecase.port.UserMapper;
import com.MemberDomain.model.request.*;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.transaction.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api")
public class MemberController {

    @Autowired
    private RegisterTransaction registerTransaction;

    @Autowired
    private LoginTransaction loginTransaction;

    @Autowired
    private RequestOtpTransaction requestOtpTransaction;

    @Autowired
    private MatchOtpTransaction matchOtpTransaction;

    @Autowired
    private ForgotPasswordTransaction forgotPasswordTransaction;

    @Autowired
    private GetProfileTransaction getProfileTransaction;

    @Autowired
    private EditProfileTransaction editProfileTransaction;

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
    public ResponseEntity<?> forgotPassword(@PathVariable String idUser, @RequestBody ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request) { ;
        return forgotPasswordTransaction.forgotPassword(idUser, forgotPasswordRequest, request.getServletPath());
    }

    // get profile
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> getProfile(@PathVariable String idUser, HttpServletRequest request) { ;
        return getProfileTransaction.getProfile(idUser,  request.getServletPath());
    }

    // edit profile
    @PutMapping("/user/{idUser}")
    public ResponseEntity<?> editProfile(@PathVariable String idUser, @RequestBody EditProfileRequest editProfileRequest, HttpServletRequest request) { ;
        return editProfileTransaction.editProfile(idUser, editProfileRequest, request.getServletPath());
    }
}