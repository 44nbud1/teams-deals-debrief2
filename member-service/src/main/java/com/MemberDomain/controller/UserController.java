package com.MemberDomain.controller;

import com.MemberDomain.usecase.port.Balance_mapper;
import com.MemberDomain.usecase.port.OTP_mapper;
import com.MemberDomain.usecase.port.UserMapper;
import com.MemberDomain.model.request.*;
import com.MemberDomain.model.response.OTPResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.RegisterLoginResponse;
import com.MemberDomain.usecase.transaction.Register;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private Register register;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Balance_mapper balanceMapper;

    @Autowired
    private OTP_mapper otpMapper;

    //get all user
    @GetMapping("/auth/user/all")
    public ResponseEntity<?> getAllUser(){
        List<RegisterLoginResponse> allUser=  userMapper.getAll();
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
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        JSONObject result = register.createAccount(registerRequest);
        return new ResponseEntity<>("jsonObject", HttpStatus.CREATED);
    }

    // insert user
//    @PostMapping("/auth/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequest register) {
//        RegisterLoginResponse checkEmail = userMapper.emailCheck(register.getEmail());
//        RegisterLoginResponse checkPhone = userMapper.phoneCheck(register.getPhoneNumber());
//        if (register.getName().isEmpty() || register.getEmail().isEmpty() ||
//                register.getPhoneNumber().isEmpty() || register.getPassword().isEmpty() ||
//                register.getConfirmPassword().isEmpty()){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("Message", "Please fill in all the forms!");
//            jsonObject.put("Code", "002");
//            jsonObject.put("Http Status", "400");
//            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
//        }
//        else if (!register.getPassword().equals(register.getConfirmPassword())){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("Message", "Password is missed-match.");
//            jsonObject.put("Code", "009");
//            jsonObject.put("Http Status", "400");
//            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
//        }
//        else if (checkEmail != null) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("Message", "Email already exists.");
//            jsonObject.put("Code", "007");
//            jsonObject.put("Http Status", "400");
//            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
//        }
//        else if(checkPhone != null){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("Message", "Phone number already exists.");
//            jsonObject.put("Code", "008");
//            jsonObject.put("Http Status", "400");
//            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
//        }
//        else{
//            String uuidUser = UUID.randomUUID().toString();
//            register.setIdUser(uuidUser);
//            userMapper.registerUser(register);
//            balanceMapper.registerBalance(register.getIdUser());
//            RegisterLoginResponse registered = userMapper.getUserData(register.getIdUser());
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("Code", "001");
//            jsonObject.put("Data", registered);
//            jsonObject.put("Message", "Registration is successful.");
//            jsonObject.put("Status", "201");
//            return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
//        }
//    }

    // insert user
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        if (login.getPhoneNumber().isEmpty() || login.getPassword().isEmpty()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Message", "Please fill in all the forms!");
            jsonObject.put("Code", "002");
            jsonObject.put("Http Status", "400");
            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
        }
        RegisterLoginResponse checkUser = userMapper.login(login.getPhoneNumber(), login.getPassword());
        if (checkUser == null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Message", "Your data do not match our records.");
            jsonObject.put("Code", "011");
            jsonObject.put("Http Status", "404");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
        else{
            RegisterLoginResponse loggedIn = userMapper.getUserData(checkUser.getIdUser());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Code", "010");
            jsonObject.put("Data", loggedIn);
            jsonObject.put("Message", "You are logged in.");
            jsonObject.put("Status", "200");
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

    // request otp
    @PostMapping("/auth/request-otp")
    public ResponseEntity<?> requestOTP(@RequestBody OtpRequest otp){
        ProfileResponse phoneCheck = userMapper.phoneOTPCheck(otp.getPhoneNumber());
        if (phoneCheck == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Status", "013");
            jsonObject.put("Message", "Phone number does not exist");
            jsonObject.put("HTTP Status", "404");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
        else{
            OTPResponse checkOTP = otpMapper.checkOTP(phoneCheck.getIdUser());
            if(checkOTP == null){
                otpMapper.createOTP(phoneCheck.getIdUser());
                JSONObject jsonObject = new JSONObject();
                JSONObject empty = new JSONObject();
                jsonObject.put("data", phoneCheck.getIdUser());
                jsonObject.put("message", "Your OTP has sent to your phone number.");
                jsonObject.put("status", "200");
                return new ResponseEntity<>(jsonObject, HttpStatus.OK);
            }
            else{
                otpMapper.updateOTP(phoneCheck.getIdUser());
                JSONObject jsonObject = new JSONObject();
                JSONObject empty = new JSONObject();
                jsonObject.put("data", phoneCheck.getIdUser());
                jsonObject.put("message", "Your OTP has sent to your phone number.");
                jsonObject.put("status", "200");
                return new ResponseEntity<>(jsonObject, HttpStatus.OK);
            }
        }
    }

    // match otp
    @PostMapping("/auth/{idUser}/match-otp")
    public ResponseEntity<?> matchOTP(@PathVariable String idUser, @RequestBody MatchOtpRequest otp){
        ProfileResponse userCheck = userMapper.getUserProfile(idUser);
        if (userCheck == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "User is not found");
            jsonObject.put("status", "404");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }

        OTPResponse checkOTP = otpMapper.checkOTP(idUser);
        if(checkOTP == null){
            JSONObject jsonObject = new JSONObject();
            JSONObject empty = new JSONObject();
            jsonObject.put("data", empty);
            jsonObject.put("message", "You need to request new OTP.");
            jsonObject.put("status", "200");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        else{
            OTPResponse matchOTP = otpMapper.matchOTP(idUser, otp.getOtp());
            if(matchOTP.getOtp() != otp.getOtp()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Status", "");
                jsonObject.put("Message", "OTP is invalid.");
                jsonObject.put("Http status", "400");
                return new ResponseEntity<>(jsonObject, HttpStatus.OK);
            }
            else{
                JSONObject jsonObject = new JSONObject();
                JSONObject empty = new JSONObject();
                jsonObject.put("data", empty);
                jsonObject.put("message", "OTP Match.");
                jsonObject.put("status", "200");
                return new ResponseEntity<>(jsonObject, HttpStatus.OK);
            }
        }
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
}