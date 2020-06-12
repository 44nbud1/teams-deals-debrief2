package com.MemberDomain.usecase.validation;

import com.MemberDomain.model.request.*;
import com.MemberDomain.usecase.exception.*;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserValidation {

    private final String regex_email = "^[\\w!#$%&’+/=?`{|}~^-]+(?:\\.[\\w!#$%&’+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String regex_password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
    private final String regex_telephone = "^[\\+]{1}[0-9]{11,13}$";
    private final String regex_name = "^(?=.{1,9}[a-zA-Z\\'\\-][ ])(?=.*[\\s])(?!.*[0-9])(?!.*[!@#$%^&*]).{3,20}$|^(?=.*[a-zA-Z\\'\\-])(?!.*[0-9])(?!.*[!@#$%^&*]).{3,10}$";
    private final String regex_otp = "[0-9]{4}";

    public void register(RegisterRequest registerRequest) {

        if (registerRequest.getName().isEmpty() || registerRequest.getEmail().isEmpty() ||
                registerRequest.getPhoneNumber().isEmpty() || registerRequest.getPassword().isEmpty() ||
                registerRequest.getConfirmPassword().isEmpty()){
            throw new RegisterException("Please fill in all the forms!", HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches(regex_name, registerRequest.getName())){
            throw new RegisterException("Name is invalid.", HttpStatus.BAD_REQUEST);
        }
        if (!Pattern.matches(regex_email, registerRequest.getEmail())) {
            throw new RegisterException("Email is invalid.", HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches(regex_password, registerRequest.getPassword())) {
            throw new RegisterException("Password is invalid.", HttpStatus.BAD_REQUEST);
        }

        if(!Pattern.matches(regex_telephone, registerRequest.getPhoneNumber())){
            throw new RegisterException("Phone Number is invalid.", HttpStatus.BAD_REQUEST);
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new RegisterException("Password is missed match.", HttpStatus.BAD_REQUEST);
        }
    }

    public void login(LoginRequest loginRequest){

        if (loginRequest.getPhoneNumber() == null || loginRequest.getPassword()== null){
            throw new LoginException("Please fill in all the forms!", HttpStatus.BAD_REQUEST);
        }

        if(!Pattern.matches(regex_telephone, loginRequest.getPhoneNumber())){
            throw new LoginException("Phone Number is invalid.", HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches(regex_password, loginRequest.getPassword())) {
            throw new LoginException("Password is invalid.", HttpStatus.BAD_REQUEST);
        }

    }

    public void requestOtp(OtpRequest otpRequest){

        if (otpRequest.getPhoneNumber() == null){
            throw new RequestOtpException("Please fill in all the forms.", HttpStatus.BAD_REQUEST);
        }

        if(!Pattern.matches(regex_telephone, ""+otpRequest.getPhoneNumber())){
            throw new RequestOtpException("Phone Number is invalid.", HttpStatus.BAD_REQUEST);
        }
    }

    public void matchOtp(MatchOtpRequest matchOtpRequest){

        if (matchOtpRequest.getOtp() == null){
            throw new MatchOtpException("Please fill in all the forms!", HttpStatus.BAD_REQUEST);
        }

        if(!Pattern.matches(regex_otp, ""+matchOtpRequest.getOtp())){
            throw new MatchOtpException("OTP is invalid.", HttpStatus.BAD_REQUEST);
        }
    }

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest){

        if (forgotPasswordRequest.getPassword() == null ||forgotPasswordRequest.getConfirmPassword() == null){
            throw new ForgotPasswordException("Please fill in all the forms!", HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches(regex_password, forgotPasswordRequest.getPassword())){
            throw new ForgotPasswordException("Password is invalid.", HttpStatus.BAD_REQUEST);
        }

        if (!forgotPasswordRequest.getPassword().equals(forgotPasswordRequest.getConfirmPassword())){
            throw new ForgotPasswordException("Password is missed match.", HttpStatus.BAD_REQUEST);
        }

    }
}

