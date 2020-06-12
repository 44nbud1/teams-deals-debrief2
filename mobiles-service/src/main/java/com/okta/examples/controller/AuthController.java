package com.okta.examples.controller;

import com.okta.examples.model.request.ForgotPasswordRequest;
import com.okta.examples.model.request.LoginRequest;
import com.okta.examples.model.request.RegisterRequest;
import com.okta.examples.service.usecase.AuthenticationService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    AuthenticationService authentication;


    @GetMapping("/")
    public ResponseEntity<?> welcome(HttpServletRequest request){
        return new ResponseEntity<>("Welcome. Your session id : "+request.getSession().getId(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request){
        return authentication.register(registerRequest, request.getServletPath());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return authentication.login(loginRequest, request.getServletPath());
    }

    @PostMapping(value ="/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody JSONObject data, HttpServletRequest request){
        return authentication.requestOtp(data, request.getServletPath());
    }

    @PostMapping(value ="/{idUser}/match-otp")
    public ResponseEntity<?> matchOtp(@PathVariable("idUser") String idUser,
                                      @RequestBody JSONObject data, HttpServletRequest request){
        return authentication.matchOtp(idUser, data, request.getServletPath());
    }

    @PostMapping(value = "/{idUser}/forgot-password")
    public ResponseEntity<?> forgotPassword(@PathVariable("idUser") String idUser,
                                            @RequestBody ForgotPasswordRequest forgotPasswordRequest,
                                            HttpServletRequest request){
        return authentication.forgotPassword(idUser, forgotPasswordRequest, request.getServletPath());
    }

}
