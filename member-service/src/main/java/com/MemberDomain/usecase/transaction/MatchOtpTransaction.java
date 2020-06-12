package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.wrapper.ResponseWrapper;
import com.MemberDomain.model.request.MatchOtpRequest;
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
public class MatchOtpTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public JSONObject matchOtp(String idUser, MatchOtpRequest matchOtp){

        userValidation.matchOtp(matchOtp);

        ProfileResponse profileResponse = userRepository.getUserProfile(""+idUser);

        if (profileResponse == null){
            throw new RegisterException("User is not found.", HttpStatus.NOT_FOUND);
        }


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


