package com.MemberDomain.usecase.port;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.*;

public interface MemberRepository {
    Boolean doesEmailAvailable(String email);
    Boolean doesPhoneNumberAvailable(String phoneNumber);
    void insertNewUser(RegisterRequest registerRequest);
    void insertNewUserBalance(String idUser);
    LoginResponse getUserLoginData(String phoneNumber);
    ProfileResponse getUserProfile(String idUser);
    UserDataResponse getUserData(String idUser);
    ProfileResponse requestOtp(String phoneNumber);
    OtpResponse checkUserOtp(String idUser);
    void createOtp(String idUser);
    void updateOtp(String idUser);
    void deleteOtp(String idUser);
    OtpResponse matchOtp(String idUser, String otp);
    OtpResponse matchOtpDate(String idUser, String otp);
    void matchingOtp(String idUser);
    void unmatchingOtp(String idUser);
    PasswordResponse getUserPassword(String idUser);
    void updatePassword(String idUser, String password);
    void updateName(String idUser, String name);
    void updateEmail(String idUser, String email);
}