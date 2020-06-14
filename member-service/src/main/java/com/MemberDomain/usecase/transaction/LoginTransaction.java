package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.LoginRequest;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> login(LoginRequest loginRequest, String path){

        ResponseEntity<?> check = userValidation.login(loginRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        if (loginRequest.getPhoneNumber().startsWith("0")){
            loginRequest.setPhoneNumber("+62"+loginRequest.getPhoneNumber().substring(1));
        }

        ProfileResponse profileResponse = userRepository.doLogin(""+loginRequest.getPhoneNumber(),
                ""+loginRequest.getPassword());

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_NOT_MATCH, path);
        }

        UserDataResponse userDataResponse = userRepository.getUserData(profileResponse.getIdUser());

        return ResponseSuccess.wrapResponse(userDataResponse, DealsStatus.LOGIN_SUCCESS, path);
    }
}

