package com.okta.examples.service.validation;

import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.request.EditProfileRequest;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserValidation {

    private final String regex_email = "^[\\w!#$%&’+/=?`{|}~^-]+(?:\\.[\\w!#$%&’+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String regex_password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
    private final String regex_name = "^(?=.{1,9}[a-zA-Z\\'\\-][ ])(?=.*[\\s])(?!.*[0-9])(?!.*[!@#$%^&*]).{3,20}$|^(?=.*[a-zA-Z\\'\\-])(?!.*[0-9])(?!.*[!@#$%^&*]).{3,10}$";

    public boolean testMe(){
        return true;
    }

    public ResponseEntity<?> editProfile(EditProfileRequest editProfileRequest, String path){

        if (editProfileRequest == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }
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
                if (!Pattern.matches(regex_password, editProfileRequest.getOldPassword())){
                    return ResponseFailed.wrapResponse(DealsStatus.PASSWORD_INVALID, path);
                }

                if (!Pattern.matches(regex_password, editProfileRequest.getNewPassword())){
                    return ResponseFailed.wrapResponse(DealsStatus.NEW_PASSWORD_INVALID, path);
                }

                if (!editProfileRequest.getConfirmPassword().equals(editProfileRequest.getNewPassword())){
                    return ResponseFailed.wrapResponse(DealsStatus.NEW_PASSWORD_MISS_MATCH, path);
                }

            }else {
                return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
            }
        }

        if (!content){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ONE_FIELD, path);
        }

        return ResponseSuccess.wrapOk();
    }

}
