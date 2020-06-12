package com.MemberDomain.usecase.transaction;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.usecase.exception.RegisterException;
import com.MemberDomain.usecase.port.UserMapper;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class Register{

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public JSONObject createAccount(RegisterRequest registerRequest){

        userValidation.register(registerRequest);

        if (userRepository.doesEmailAvailable(""+registerRequest.getEmail()) == Boolean.FALSE){
            throw new RegisterException("Email already exist.", HttpStatus.NOT_FOUND);
        }

        if (userRepository.doesPhoneNumberAvailable(""+registerRequest.getPhoneNumber()) == Boolean.FALSE){
            throw new RegisterException("Phone number already exist.", HttpStatus.NOT_FOUND);
        }

        return null;
    }
}

