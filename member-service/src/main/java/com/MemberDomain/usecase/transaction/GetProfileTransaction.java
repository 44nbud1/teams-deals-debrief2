package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetProfileTransaction {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<JSONObject> getProfile(String idUser, String path){

        ProfileResponse profileResponse = userRepository.getUserProfile(idUser);

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, path);
        }

        return ResponseSuccess.wrapResponse(profileResponse, DealsStatus.PROFILE_COLLECTED, path);
    }
}

