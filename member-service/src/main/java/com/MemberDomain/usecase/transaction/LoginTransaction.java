package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.LoginRequest;
import com.MemberDomain.model.response.LoginResponse;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<JSONObject> login(LoginRequest loginRequest, String path){

        ResponseEntity<JSONObject> check = userValidation.login(loginRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        if (loginRequest.getPhoneNumber().startsWith("0")){
            loginRequest.setPhoneNumber("+62"+loginRequest.getPhoneNumber().substring(1));
        }

        LoginResponse loginResponse = userRepository.getUserLoginData(""+loginRequest.getPhoneNumber());

        if (loginResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_NOT_MATCH, path);
        }

        if (!decode(loginRequest.getPassword(), loginResponse.getPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_NOT_MATCH, path);
        }

        UserDataResponse userDataResponse = userRepository.getUserData(loginResponse.getIdUser());

        return ResponseSuccess.wrapResponse(userDataResponse, DealsStatus.LOGIN_SUCCESS, path);
    }

    public boolean decode(String password, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }
}

