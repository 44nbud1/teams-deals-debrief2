package com.MemberDomain.adapter.repository;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.OtpResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.port.BalanceMapper;
import com.MemberDomain.usecase.port.OtpMapper;
import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.port.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UserMapper userMapper;

    @Autowired
    BalanceMapper balanceMapper;

    @Autowired
    OtpMapper otpMapper;

    @Override
    public Boolean doesEmailAvailable(String email) {
        if (userMapper.emailCheck(email) != null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean doesPhoneNumberAvailable(String phoneNumber) {
        if (userMapper.phoneCheck(phoneNumber) != null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public void insertNewUser(RegisterRequest registerRequest) {
        userMapper.registerUser(registerRequest);
        balanceMapper.registerBalance(registerRequest.getIdUser());
    }

    @Override
    public ProfileResponse doLogin(String phoneNumber, String password) {
        ProfileResponse profileResponse = userMapper.login(phoneNumber,password);
        return profileResponse;
    }

    @Override
    public ProfileResponse getUserProfile(String idUser) {
        ProfileResponse profileResponse = userMapper.getUserProfile(idUser);
        return profileResponse;
    }

    @Override
    public UserDataResponse getUserData(String idUser) {
        UserDataResponse userDataResponse = userMapper.getUserData(idUser);
        return userDataResponse;
    }

    @Override
    public ProfileResponse requestOtp(String phoneNumber) {
        ProfileResponse profileResponse = userMapper.phoneOTPCheck(phoneNumber);
        return profileResponse;
    }

    @Override
    public OtpResponse checkUserOtp(String idUser) {
        OtpResponse otpResponse = otpMapper.checkOTP(idUser);
        return otpResponse;
    }

    @Override
    public void createOtp(String idUser) {
        otpMapper.createOTP(idUser);
    }

    @Override
    public void updateOtp(String idUser) {
        otpMapper.updateOTP(idUser);
    }
}