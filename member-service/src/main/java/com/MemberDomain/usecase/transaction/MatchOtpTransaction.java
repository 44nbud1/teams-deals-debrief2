package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.MatchOtpRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MatchOtpTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> matchOtp(String idUser, MatchOtpRequest matchOtp, String path){

        ResponseEntity<?> check = userValidation.matchOtp(matchOtp, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ProfileResponse profileResponse = userRepository.getUserProfile(""+idUser);

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, path);
        }

        OtpResponse checkUserOtp = userRepository.checkUserOtp(idUser);

        if(checkUserOtp == null){
            return ResponseFailed.wrapResponse(DealsStatus.REQUEST_NEW_OTP, path);
        }

        String userOtp = matchOtp.getOtp();
        OtpResponse matchingOtp = userRepository.matchOtp(""+idUser, ""+userOtp);

        if(matchingOtp == null) {
            return ResponseFailed.wrapResponse(DealsStatus.OTP_NOT_MATCH, path);
        }

        if(matchingOtp.getMatchStatus() == 1){
            return ResponseFailed.wrapResponse(DealsStatus.REQUEST_NEW_OTP, path);
        }

        OtpResponse matchingOtpDate = userRepository.matchOtpDate(""+idUser, ""+userOtp);

        if (matchingOtpDate == null){
            return ResponseFailed.wrapResponse(DealsStatus.OTP_EXPIRED, path);
        }

        userRepository.matchingOtp(idUser);

        return ResponseSuccess.wrapResponse(null, DealsStatus.OTP_MATCH, path);
    }
}


