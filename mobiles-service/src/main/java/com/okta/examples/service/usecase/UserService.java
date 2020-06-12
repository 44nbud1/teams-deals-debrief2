package com.okta.examples.service.usecase;

import com.okta.examples.adapter.parser.Parser;
import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.request.EditProfileRequest;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import com.okta.examples.service.microservice.MemberDomain;
import com.okta.examples.service.validation.UserValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    MemberDomain member;

    @Autowired
    UserValidation validate;

    @Autowired
    SessionService sessionService;

    public ResponseEntity<?> getProfile(String idUser, String path){

        //Get profil in member domain
        ResponseEntity<?> fromMember = member.getProfile(idUser);
        System.out.println("Profile. Receive data from member domain :"+ fromMember.getBody().toString());

        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
        String message = ""+ jsonMember.get("message");
        String status = ""+ jsonMember.get("status");

        //Check response
        if (!fromMember.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromMember.getStatusCode(), path);
        }

        JSONObject user = (JSONObject) jsonMember.get("data");

        //Wrap response
        return ResponseSuccess.wrapResponse(user, DealsStatus.PROFILE_COLLECTED, path);
//        return ResponseSuccess.wrap200(user, "Success",
//                "/api/user/"+idUser);
    }

    public ResponseEntity<?> editProfile(String idUser, EditProfileRequest editProfileRequest, String path){

        //Edit profile validation
        ResponseEntity<?> check = validate.editProfile(editProfileRequest, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }
        //Edit profile validation in member domain
        System.out.println("Edit Profile. Send data to member domain : "+ Parser.toJsonString(editProfileRequest));
        ResponseEntity<?> fromMember = member.editProfile(idUser, editProfileRequest);
        System.out.println("Edit Profile. Receive data from member domain :"+ fromMember.getBody().toString());

        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
        String message = ""+ jsonMember.get("message");
        String status = ""+ jsonMember.get("status");

        //Check response
        if (!fromMember.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromMember.getStatusCode(), path);
        }

        JSONObject user = (JSONObject) jsonMember.get("data");

        //Wrap response
        return ResponseSuccess.wrapResponse(user, DealsStatus.PROFILE_UPDATED, path);
//        return ResponseSuccess.wrap200(null, "Success",
//                "/api/user/"+idUser);
    }

    public ResponseEntity<?> logout(String idUser, String path){

        //Logout in member domain
//        ResponseEntity<?> fromMember = member.logout(idUser);
        System.out.println("Logout. "+ new Date());
//
//        JSONObject jsonMember = Parser.parseJSON(fromMember.getBody().toString());
//        String message = ""+ jsonMember.get("message");
//
//        //Check response
//        if (!fromMember.getStatusCode().is2xxSuccessful()){
//            throw new LogoutException(message, fromMember.getStatusCode());
//        }

        //Destroy session
        System.out.println("Destroy Session for id :" + idUser);
        sessionService.destroySession(idUser);

        //Wrap response
        return ResponseSuccess.wrapResponse(null, DealsStatus.LOGOUT_SUCCESS, path);
//        return ResponseSuccess.wrap200(null, "You are logged out",
//                "/api/user/"+idUser);
    }

}
