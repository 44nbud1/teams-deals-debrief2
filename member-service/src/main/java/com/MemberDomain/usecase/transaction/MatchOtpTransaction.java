package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.wrapper.ResponseWrapper;
import com.MemberDomain.model.request.MatchOtpRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.exception.MatchOtpException;
import com.MemberDomain.usecase.exception.RegisterException;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.joda.time.DateTime;
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

        String userOtp = matchOtp.getOtp();
        OtpResponse matchingOtp = userRepository.matchOtp(""+idUser, ""+userOtp);

        if(matchingOtp == null || !userOtp.equals(matchingOtp.getOtp())) {
            return ResponseWrapper.wrap("OTP Not Match.", 200, matchingOtp);
        }

        if (userOtp.equals(matchingOtp.getOtp())){
            return ResponseWrapper.wrap("OTP Match.", 200, matchingOtp);
        }

        JSONObject result = new JSONObject();
        return ResponseWrapper.wrap("OTP Match.", 200, result);
    }
}


