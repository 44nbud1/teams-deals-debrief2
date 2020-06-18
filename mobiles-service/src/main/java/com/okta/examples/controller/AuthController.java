package com.okta.examples.controller;

import com.okta.examples.adapter.parser.Parser;
import com.okta.examples.adapter.template.Template;
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

    @Autowired
    private Template template;

    @GetMapping("/")
    public ResponseEntity<?> welcome(HttpServletRequest request){
        System.out.println("Test Welcome");
        return new ResponseEntity<>("Welcome to DANA Deals gitu gitu.", HttpStatus.OK);
    }

    @PostMapping(value = "/api/auth/register")
    public ResponseEntity<?> register(@RequestBody(required = false) RegisterRequest registerRequest, HttpServletRequest request){
        return authentication.register(registerRequest, request.getServletPath());
    }

    @PostMapping(value = "/api/auth/login")
    public ResponseEntity<?> login(@RequestBody(required = false) LoginRequest loginRequest, HttpServletRequest request) {
        request.getSession().invalidate();
        ResponseEntity<JSONObject> login = authentication.login(loginRequest, request.getServletPath(), request.getSession().getId());
//        JSONObject data = (JSONObject) login.getBody().get("data");
//        JSONObject user = (JSONObject) data.get("user");
//        if (user != null){
////        request.getSession().setAttribute("userId", data.get("token"));
////        System.out.println(user.get("name"));
////        request.getSession().setAttribute("id", user.get("idUser"));
////        System.out.println(request.getSession().getAttribute("userId"));
//        }
        return login;
    }

    @PostMapping(value ="/api/auth/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody(required = false) JSONObject data, HttpServletRequest request){
        return authentication.requestOtp(data, request.getServletPath());
    }

    @PostMapping(value ="/api/auth/{idUser}/match-otp")
    public ResponseEntity<?> matchOtp(@PathVariable("idUser") String idUser,
                                      @RequestBody(required = false) JSONObject data, HttpServletRequest request){
        return authentication.matchOtp(idUser, data, request.getServletPath());
    }

    @PostMapping(value = "/api/auth/{idUser}/forgot-password")
    public ResponseEntity<?> forgotPassword(@PathVariable("idUser") String idUser,
                                            @RequestBody(required = false) ForgotPasswordRequest forgotPasswordRequest,
                                            HttpServletRequest request){
        return authentication.forgotPassword(idUser, forgotPasswordRequest, request.getServletPath());
    }

    @GetMapping(value = "/tes")
    public ResponseEntity<?> test(HttpServletRequest request){
        System.out.println(request.getSession().getId());
        return template.poest("http://localhost:8082/test", request.getSession().getId());
    }
}
