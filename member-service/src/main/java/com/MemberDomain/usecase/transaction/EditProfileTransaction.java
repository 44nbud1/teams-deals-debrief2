package com.MemberDomain.usecase.transaction;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.EditProfileRequest;
import com.MemberDomain.model.response.PasswordResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.usecase.port.MemberRepository;
import com.MemberDomain.usecase.validation.MemberValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EditProfileTransaction {

    @Autowired
    MemberValidation memberValidation;

    @Autowired
    MemberRepository memberRepository;

    public ResponseEntity<JSONObject> editProfile(String idUser, EditProfileRequest editProfileRequest, String path){

        ResponseEntity<JSONObject> check = memberValidation.editProfile(editProfileRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ProfileResponse profileResponse = memberRepository.getUserProfile(""+idUser);

        if (profileResponse == null){
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, path);
        }

        if (editProfileRequest.getNewPassword() != null) {
            PasswordResponse passwordResponse = memberRepository.getUserPassword(idUser);
            if (!decode(editProfileRequest.getOldPassword(), passwordResponse.getPassword())) {
                return ResponseFailed.wrapResponse(DealsStatus.OLD_PASSWORD_NOT_MATCH, path);
            }
            else{
                ResponseEntity<JSONObject> checkEditPassword = memberValidation.editPassword(editProfileRequest, path);
                if (!checkEditPassword.getStatusCode().is2xxSuccessful()){
                    return checkEditPassword;
                }
                else{
                    editProfileRequest.setNewPassword(encryptPassword(editProfileRequest.getNewPassword()));
                    memberRepository.updatePassword(idUser, editProfileRequest.getNewPassword());
                }
            }
        }

        if (editProfileRequest.getName() != null) {
            memberRepository.updateName(idUser, editProfileRequest.getName());
        }

        if (editProfileRequest.getEmail() != null) {
            if (memberRepository.doesEmailAvailable(""+editProfileRequest.getEmail().toLowerCase()) == Boolean.FALSE){
                return ResponseFailed.wrapResponse(DealsStatus.EMAIL_EXISTS, path);
            }
            else{
                editProfileRequest.setEmail(editProfileRequest.getEmail().toLowerCase());
                memberRepository.updateEmail(idUser, editProfileRequest.getEmail());
            }
        }

        ProfileResponse newProfileResponse = memberRepository.getUserProfile(""+idUser);
        return ResponseSuccess.wrapResponse(newProfileResponse, DealsStatus.PROFILE_UPDATED, path);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean decode(String password, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }
}



