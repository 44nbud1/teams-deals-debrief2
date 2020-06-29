package com.MemberDomain.usecase.validation;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.wrapper.ResponseFailed;
import com.MemberDomain.adapter.wrapper.ResponseSuccess;
import com.MemberDomain.model.request.*;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MemberValidation {

    private final String regex_email = "^(?=.*(^[\\w]+|^[\\w]+[.\\w]+[\\w])[@][\\w.\\-]+[.][A-Za-z]+$).{6,74}$";
    private final String regex_password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,16}$";
    private final String regex_telephone = "^(?!.*[^0-9+])(([\\+][6][2])|([0]){1})[8]{1}[^046]{1}[0-9]{7,9}$";
    private final String regex_name = "^(?!.*^[\\s])(?!.*[\\s]$)(?!.*[0-9!@#$%^&*])(?=.*[a-zA-Z'\\-]{3,10}[\\s]|.*^[a-zA-Z'\\-]{3,10}$)(?!.*['][\\-]|.*[\\-][']|.*[\\-][\\-]|.*['][']).{3,20}$";
    private final String regex_otp = "[0-9]{4}";

    public ResponseEntity<JSONObject> register(RegisterRequest registerRequest, String path) {

        if (registerRequest == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (registerRequest.getEmail() == null || registerRequest.getPhoneNumber() == null ||
                registerRequest.getPassword()== null || registerRequest.getName() == null ||
                registerRequest.getConfirmPassword() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (!Pattern.matches(regex_name, registerRequest.getName())){
            return ResponseFailed.wrapResponse(DealsStatus.NAME_INVALID, path);
        }
        if (!Pattern.matches(regex_email, registerRequest.getEmail())) {
            return ResponseFailed.wrapResponse(DealsStatus.EMAIL_INVALID, path);
        }

        if (!Pattern.matches(regex_password, registerRequest.getPassword())) {
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_INVALID, path);
        }
        if (registerRequest.getPhoneNumber().startsWith("0")){
            registerRequest.setPhoneNumber("+62"+registerRequest.getPhoneNumber().substring(1));
        }
        if(!Pattern.matches(regex_telephone, registerRequest.getPhoneNumber())){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_INVALID, path);
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_MISS_MATCH, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> login(LoginRequest loginRequest, String path){

        if (loginRequest == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (loginRequest.getPhoneNumber() == null || loginRequest.getPassword()== null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }
        if (loginRequest.getPhoneNumber().startsWith("0")){
            loginRequest.setPhoneNumber("+62"+loginRequest.getPhoneNumber().substring(1));
        }
        if(!Pattern.matches(regex_telephone, loginRequest.getPhoneNumber())){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_INVALID, path);
        }

        if (!Pattern.matches(regex_password, loginRequest.getPassword())) {
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_INVALID, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> requestOtp(OtpRequest otpRequest, String path){

        if (otpRequest == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (otpRequest.getPhoneNumber() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (otpRequest.getPhoneNumber().startsWith("0")){
            otpRequest.setPhoneNumber("+62"+otpRequest.getPhoneNumber().substring(1));
        }

        if(!Pattern.matches(regex_telephone, ""+otpRequest.getPhoneNumber())){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_INVALID, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> matchOtp(MatchOtpRequest matchOtpRequest, String path){

        if (matchOtpRequest == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (matchOtpRequest.getOtp() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if(!Pattern.matches(regex_otp, ""+matchOtpRequest.getOtp())){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> forgotPassword(ForgotPasswordRequest forgotPasswordRequest, String path){

        if (forgotPasswordRequest == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (forgotPasswordRequest.getNewPassword() == null || forgotPasswordRequest.getConfirmPassword() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (!Pattern.matches(regex_password, forgotPasswordRequest.getNewPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_INVALID, path);
        }

        if (!forgotPasswordRequest.getNewPassword().equals(forgotPasswordRequest.getConfirmPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_MISS_MATCH, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> editProfile(EditProfileRequest editProfileRequest, String path){

        boolean content = false;

        if (editProfileRequest.getName() != null) {
            content = true;
            if (!Pattern.matches(regex_name, editProfileRequest.getName())) {
                return ResponseFailed.wrapResponse(DealsStatus.NAME_INVALID, path);
            }
        }

        if (editProfileRequest.getEmail() != null){
            content = true;
            if (!Pattern.matches(regex_email, editProfileRequest.getEmail())) {
                return ResponseFailed.wrapResponse(DealsStatus.EMAIL_INVALID, path);
            }

        }

        if (editProfileRequest.getOldPassword() != null || editProfileRequest.getNewPassword() != null ||
                editProfileRequest.getConfirmPassword() != null) {

            if (editProfileRequest.getOldPassword() != null && editProfileRequest.getNewPassword() != null &&
                    editProfileRequest.getConfirmPassword() != null){

                content = true;

            }else {
                return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
            }
        }

        if (!content){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ONE_FIELD, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> editPassword(EditProfileRequest editProfileRequest, String path){

        if (!Pattern.matches(regex_password, editProfileRequest.getOldPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_INVALID, path);
        }

        if (!Pattern.matches(regex_password, editProfileRequest.getNewPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.NEW_PASSWORD_INVALID, path);
        }

        if (!editProfileRequest.getConfirmPassword().equals(editProfileRequest.getNewPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.NEW_PASSWORD_MISS_MATCH, path);
        }

        return ResponseSuccess.wrapOk();
    }
}

