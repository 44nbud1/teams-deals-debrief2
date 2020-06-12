package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.wrapper.ResponseWrapper;
import com.MemberDomain.model.request.LoginRequest;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.exception.RegisterException;
import com.MemberDomain.usecase.port.UserMapper;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public JSONObject login(LoginRequest loginRequest){

        userValidation.login(loginRequest);

        String phoneNumber = loginRequest.getPhoneNumber();
        String password = loginRequest.getPassword();

        ProfileResponse profileResponse = userRepository.doLogin(""+phoneNumber, ""+password);

        if (profileResponse == null){
            throw new RegisterException("Your data do not match our records.", HttpStatus.NOT_FOUND);
        }

        UserDataResponse userDataResponse = userRepository.getUserData(profileResponse.getIdUser());

        return ResponseWrapper.wrap("Your registration is successful.", 200, userDataResponse);
    }
}

