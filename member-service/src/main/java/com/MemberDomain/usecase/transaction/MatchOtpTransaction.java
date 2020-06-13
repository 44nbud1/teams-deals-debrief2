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


import java.text.SimpleDateFormat;
import java.util.Date;

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

        String otp = matchOtp.getOtp();
        OtpResponse otpResponse = userRepository.matchOtp(""+idUser, ""+otp);

        DateTime expiredDate = otpResponse.getExpiredDate();
        DateTime currentDate = new DateTime();

        if (otp == otpResponse.getOtp()){
            throw new MatchOtpException("Otp Not Match.", HttpStatus.NOT_FOUND);
        }

        if(currentDate.compareTo(expiredDate) > 0){
            return ResponseWrapper.wrap("Your OTP expired.", 200, idUser);
        }

        JSONObject result = new JSONObject();
        return ResponseWrapper.wrap("OTP Match.", 200, result);
    }
}


