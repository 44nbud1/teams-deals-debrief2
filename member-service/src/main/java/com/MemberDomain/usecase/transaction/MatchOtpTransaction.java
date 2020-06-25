package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.MatchOtpRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.MemberRepository;
import com.MemberDomain.usecase.validation.MemberValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MatchOtpTransaction {

    @Autowired
    MemberValidation memberValidation;

    @Autowired
    MemberRepository memberRepository;

    public ResponseEntity<?> matchOtp(String idUser, MatchOtpRequest matchOtp, String path){

        ResponseEntity<?> check = memberValidation.matchOtp(matchOtp, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ProfileResponse profileResponse = memberRepository.getUserProfile(""+idUser);

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, path);
        }

        OtpResponse checkUserOtp = memberRepository.checkUserOtp(idUser);

        if(checkUserOtp == null || checkUserOtp.getMatchStatus() == 1){
            return ResponseFailed.wrapResponse(DealsStatus.REQUEST_NEW_OTP, path);
        }

        String userOtp = matchOtp.getOtp();
        OtpResponse matchingOtp = memberRepository.matchOtp(""+idUser, ""+userOtp);

        if(matchingOtp == null) {
            return ResponseFailed.wrapResponse(DealsStatus.OTP_NOT_MATCH, path);
        }

        OtpResponse matchingOtpDate = memberRepository.matchOtpDate(""+idUser, ""+userOtp);

        if (matchingOtpDate == null){
            memberRepository.deleteOtp(idUser);
            return ResponseFailed.wrapResponse(DealsStatus.OTP_EXPIRED, path);
        }

        memberRepository.matchingOtp(idUser);

        return ResponseSuccess.wrapResponse(null, DealsStatus.OTP_MATCH, path);
    }
}


