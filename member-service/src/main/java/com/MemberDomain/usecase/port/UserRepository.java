package com.MemberDomain.usecase.port;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;

public interface UserRepository {
    Boolean doesEmailAvailable(String email);
    Boolean doesPhoneNumberAvailable(String phoneNumber);
    void insertNewUser(RegisterRequest registerRequest);
    ProfileResponse doLogin(String phoneNumber, String password);
    ProfileResponse getUserProfile(String idUser);
    UserDataResponse getUserData(String idUser);
    ProfileResponse requestOtp(String phoneNumber);
    OtpResponse checkUserOtp(String idUser);
    void createOtp(String idUser);
    void updateOtp(String idUser);
    OtpResponse matchOtp(String idUser, String otp);
}