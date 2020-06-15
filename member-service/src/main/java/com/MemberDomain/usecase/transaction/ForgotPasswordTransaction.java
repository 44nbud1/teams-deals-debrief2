package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.ForgotPasswordRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> forgotPassword(String idUser, ForgotPasswordRequest forgotPasswordRequest, String path){

        ResponseEntity<?> check = userValidation.forgotPassword(forgotPasswordRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ProfileResponse profileResponse = userRepository.getUserProfile(""+idUser);

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, path);
        }

        OtpResponse otpResponse = userRepository.checkUserOtp(idUser);

        if (otpResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.REQUEST_NEW_OTP, path);
        }

        if (otpResponse.getMatchStatus() == 0){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_OTP, path);
        }

        forgotPasswordRequest.setNewPassword(encryptPassword(forgotPasswordRequest.getNewPassword()));
        userRepository.updatePassword(idUser, forgotPasswordRequest.getNewPassword());
        userRepository.unmatchingOtp(idUser);

        return ResponseSuccess.wrapResponse(null, DealsStatus.FORGOT_PASSWORD, path);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}


