package com.okta.examples.service.validation;

import com.okta.examples.model.request.ForgotPasswordRequest;
import com.okta.examples.model.request.LoginRequest;
import com.okta.examples.model.request.RegisterRequest;
import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthenticationValidation {

    private final String regex_email = "^[\\w!#$%&’+/=?`{|}~^-]+(?:\\.[\\w!#$%&’+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String regex_password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
    private final String regex_telephone = "^[\\+]{1}[0-9]{11,13}$";
    private final String regex_name = "^(?=.{1,9}[a-zA-Z\\'\\-][ ])(?=.*[\\s])(?!.*[0-9])(?!.*[!@#$%^&*]).{3,20}$|^(?=.*[a-zA-Z\\'\\-])(?!.*[0-9])(?!.*[!@#$%^&*]).{3,10}$";
    private final String regex_otp = "[0-9]{4}";

    public ResponseEntity<?> register(RegisterRequest registerRequest, String path) {

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

    public ResponseEntity<?> login(LoginRequest loginRequest, String path){

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

    public ResponseEntity<?> requestOtp(JSONObject data, String path){

        if (data == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (data.get("phoneNumber") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }
        String phoneNumber = (""+data.get("phoneNumber"));
        if (phoneNumber.startsWith("0")){
           phoneNumber = "+62"+phoneNumber.substring(1);
        }
        if(!Pattern.matches(regex_telephone, phoneNumber)){
            return ResponseFailed.wrapResponse(DealsStatus.PHONE_NUMBER_INVALID, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> matchOtp(JSONObject data, String path){

        if (data == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (data.get("otp") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if(!Pattern.matches(regex_otp, ""+data.get("otp"))){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest, String path){

        if (forgotPasswordRequest == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (forgotPasswordRequest.getPassword() == null ||forgotPasswordRequest.getConfirmPassword() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (!Pattern.matches(regex_password, forgotPasswordRequest.getPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_INVALID, path);
        }

        if (!forgotPasswordRequest.getPassword().equals(forgotPasswordRequest.getConfirmPassword())){
            return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_MISS_MATCH, path);
        }

        return ResponseSuccess.wrapOk();
    }
}
