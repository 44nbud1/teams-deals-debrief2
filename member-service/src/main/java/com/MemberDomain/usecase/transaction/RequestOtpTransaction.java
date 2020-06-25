package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.OtpRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.MemberRepository;
import com.MemberDomain.usecase.validation.MemberValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RequestOtpTransaction {

    @Autowired
    MemberValidation memberValidation;

    @Autowired
    MemberRepository memberRepository;

    public ResponseEntity<JSONObject> requestOtp(OtpRequest otpRequest, String path){

        ResponseEntity<JSONObject> check = memberValidation.requestOtp(otpRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        if (otpRequest.getPhoneNumber().startsWith("0")){
            otpRequest.setPhoneNumber("+62"+otpRequest.getPhoneNumber().substring(1));
        }

        ProfileResponse profileResponse = memberRepository.requestOtp(otpRequest.getPhoneNumber());

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_NOT_EXISTS, path);
        }

        String idUser = profileResponse.getIdUser();
        OtpResponse otpResponse = memberRepository.checkUserOtp(""+idUser);

        if (otpResponse == null){
            memberRepository.createOtp(""+idUser);
        }
        else{
            memberRepository.updateOtp(""+idUser);
        }

        JSONObject result = new JSONObject();
        result.put("idUser", idUser);
        return ResponseSuccess.wrapResponse(result, DealsStatus.REQUEST_OTP, path);
    }
}

