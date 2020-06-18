package com.MemberDomain.usecase.port;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.*;

public interface UserMapperRepository {

    void registerUser(RegisterRequest registerRequest);
    UserDataResponse emailCheck(String email);
    UserDataResponse phoneCheck(String phoneNumber);
    LoginResponse getUserLoginData(String phoneNumber);
    UserDataResponse getUserData(String idUser);
    ProfileResponse getUserProfile(String idUser);
    PasswordResponse getUserPassword(String idUser);
    ProfileResponse phoneOTPCheck(String phoneNumber);
    void changePassword(String idUser, String password);
    void changeName(String idUser, String name);
    void changeEmail(String idUser, String email);




}
