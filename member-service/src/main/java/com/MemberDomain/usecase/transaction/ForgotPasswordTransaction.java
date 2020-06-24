package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.ForgotPasswordRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.MemberRepository;
import com.MemberDomain.usecase.validation.MemberValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordTransaction {

    @Autowired
    MemberValidation memberValidation;

    @Autowired
    MemberRepository memberRepository;

    public ResponseEntity<?> forgotPassword(String idUser, ForgotPasswordRequest forgotPasswordRequest, String path){

        ResponseEntity<?> check = memberValidation.forgotPassword(forgotPasswordRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ProfileResponse profileResponse = memberRepository.getUserProfile(""+idUser);

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, path);
        }

        OtpResponse otpResponse = memberRepository.checkUserOtp(idUser);

        if (otpResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.REQUEST_NEW_OTP, path);
        }

        if (otpResponse.getMatchStatus() == 0){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_OTP, path);
        }

        forgotPasswordRequest.setNewPassword(encryptPassword(forgotPasswordRequest.getNewPassword()));
        memberRepository.updatePassword(idUser, forgotPasswordRequest.getNewPassword());
        memberRepository.deleteOtp(idUser);

        return ResponseSuccess.wrapResponse(null, DealsStatus.FORGOT_PASSWORD, path);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}


