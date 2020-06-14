package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.EditProfileRequest;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EditProfileTransaction {

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> editProfile(String idUser, EditProfileRequest editProfileRequest, String path){

        ResponseEntity<?> check = userValidation.editProfile(editProfileRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ProfileResponse profileResponse = userRepository.getUserProfile(""+idUser);

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, path);
        }

        if (editProfileRequest.getName() != null) {
            userRepository.updateName(idUser, editProfileRequest.getName());
        }

        if (editProfileRequest.getEmail() != null) {
            editProfileRequest.setEmail(editProfileRequest.getEmail().toLowerCase());
            userRepository.updateEmail(idUser, editProfileRequest.getEmail());
        }

        ProfileResponse newProfileResponse = userRepository.getUserProfile(""+idUser);
        return ResponseSuccess.wrapResponse(newProfileResponse, DealsStatus.PROFILE_UPDATED, path);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}



