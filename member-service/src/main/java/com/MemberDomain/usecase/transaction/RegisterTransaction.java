package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.broadcast.MemberBroadcaster;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class RegisterTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberBroadcaster memberBroadcaster;

    public ResponseEntity<?> createAccount(RegisterRequest registerRequest, String path){

        ResponseEntity<?> check = userValidation.register(registerRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        if (registerRequest.getPhoneNumber().startsWith("0")){
            registerRequest.setPhoneNumber("+62"+registerRequest.getPhoneNumber().substring(1));
        }

        if (userRepository.doesEmailAvailable(""+registerRequest.getEmail()) == Boolean.FALSE){
            return ResponseFailed.wrapResponse(DealsStatus.EMAIL_EXISTS, path);
        }

        if (userRepository.doesPhoneNumberAvailable(""+registerRequest.getPhoneNumber()) == Boolean.FALSE){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_EXISTS, path);
        }

        registerRequest.setPassword(encryptPassword(registerRequest.getPassword()));
        userRepository.insertNewUser(registerRequest);

        UserDataResponse userDataResponse = userRepository.getUserData(registerRequest.getIdUser());
        memberBroadcaster.send(registerRequest.getIdUser());

        return ResponseSuccess.wrapResponse(userDataResponse, DealsStatus.REGISTRATION_SUCCESS, path);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

