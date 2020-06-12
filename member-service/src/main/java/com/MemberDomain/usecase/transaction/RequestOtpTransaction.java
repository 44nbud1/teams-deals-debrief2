package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.wrapper.ResponseWrapper;
import com.MemberDomain.model.request.OtpRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.exception.RegisterException;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RequestOtpTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public JSONObject requestOtp(OtpRequest otpRequest){

        userValidation.requestOtp(otpRequest);

        String phoneNumber = otpRequest.getPhoneNumber();
        ProfileResponse profileResponse = userRepository.requestOtp(""+phoneNumber);

        if (profileResponse == null){
            throw new RegisterException("The phone number does not exist.", HttpStatus.NOT_FOUND);
        }

        String idUser = profileResponse.getIdUser();
        OtpResponse otpResponse = userRepository.checkUserOtp(""+idUser);

        if (otpResponse == null){
            userRepository.createOtp(""+idUser);
            return ResponseWrapper.wrap("Your OTP has sent to your phone number.", 200, idUser);
        }

        userRepository.updateOtp(""+idUser);
        JSONObject result = new JSONObject();
        result.put("idUser", idUser);
        return ResponseWrapper.wrap("Your OTP has sent to your phone number.", 200, result);
    }
}

