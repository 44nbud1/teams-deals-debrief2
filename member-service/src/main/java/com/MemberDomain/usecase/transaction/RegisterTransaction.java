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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class RegisterTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberBroadcaster memberBroadcaster;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = InternalError.class)
    public ResponseEntity<?> createAccount(RegisterRequest registerRequest, String path) {

        ResponseEntity<?> check = userValidation.register(registerRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        if (registerRequest.getPhoneNumber().startsWith("0")){
            registerRequest.setPhoneNumber("+62"+registerRequest.getPhoneNumber().substring(1));
        }

        if (userRepository.doesEmailAvailable(""+registerRequest.getEmail().toLowerCase()) == Boolean.FALSE){
            return ResponseFailed.wrapResponse(DealsStatus.EMAIL_EXISTS, path);
        }

        if (userRepository.doesPhoneNumberAvailable(""+registerRequest.getPhoneNumber()) == Boolean.FALSE){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_EXISTS, path);
        }

        registerRequest.setEmail(registerRequest.getEmail().toLowerCase());
        registerRequest.setPassword(encryptPassword(registerRequest.getPassword()));

        try {
            userRepository.insertNewUser(registerRequest);
            userRepository.insertNewUserBalance(registerRequest.getIdUser());
        } catch (InternalError e) {
            return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
        }

//        userRepository.insertNewUser(registerRequest);
//        userRepository.insertNewUserBalance(registerRequest.getIdUser());

        UserDataResponse userDataResponse = userRepository.getUserData(registerRequest.getIdUser());
        memberBroadcaster.send(registerRequest.getIdUser(), Double.valueOf(0));

        return ResponseSuccess.wrapResponse(userDataResponse, DealsStatus.REGISTRATION_SUCCESS, path);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

