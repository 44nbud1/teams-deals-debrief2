package com.MemberDomain.adapter.repository;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.LoginResponse;
import com.MemberDomain.model.response.PasswordResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.port.UserMapperRepository;
import org.springframework.stereotype.Service;

@Service
public class UserMapperRepositoryImpl implements UserMapperRepository {

    @Override
    public void registerUser(RegisterRequest registerRequest) {

    }

    @Override
    public UserDataResponse emailCheck(String email) {
        return null;
    }

    @Override
    public UserDataResponse phoneCheck(String phoneNumber) {
        return null;
    }

    @Override
    public LoginResponse getUserLoginData(String phoneNumber) {
        return null;
    }

    @Override
    public UserDataResponse getUserData(String idUser) {
        return null;
    }

    @Override
    public ProfileResponse getUserProfile(String idUser) {
        return null;
    }

    @Override
    public PasswordResponse getUserPassword(String idUser) {
        return null;
    }

    @Override
    public ProfileResponse phoneOTPCheck(String phoneNumber) {
        return null;
    }

    @Override
    public void changePassword(String idUser, String password) {

    }

    @Override
    public void changeName(String idUser, String name) {

    }

    @Override
    public void changeEmail(String idUser, String email) {

    }
}
