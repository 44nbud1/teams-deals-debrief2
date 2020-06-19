package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.OtpRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RequestOtpTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<JSONObject> requestOtp(OtpRequest otpRequest, String path){

        ResponseEntity<JSONObject> check = userValidation.requestOtp(otpRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        if (otpRequest.getPhoneNumber().startsWith("0")){
            otpRequest.setPhoneNumber("+62"+otpRequest.getPhoneNumber().substring(1));
        }

        ProfileResponse profileResponse = userRepository.requestOtp(otpRequest.getPhoneNumber());

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_NOT_EXISTS, path);
        }

        String idUser = profileResponse.getIdUser();
        OtpResponse otpResponse = userRepository.checkUserOtp(""+idUser);

        if (otpResponse == null){
            userRepository.createOtp(""+idUser);
        }
        else{
            userRepository.updateOtp(""+idUser);
        }

        JSONObject result = new JSONObject();
        result.put("idUser", idUser);
        return ResponseSuccess.wrapResponse(result, DealsStatus.REQUEST_OTP, path);
    }
}

