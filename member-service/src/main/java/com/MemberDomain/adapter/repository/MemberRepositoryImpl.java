package com.MemberDomain.adapter.repository;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.*;
import com.MemberDomain.usecase.port.BalanceMapper;
import com.MemberDomain.usecase.port.OtpMapper;
import com.MemberDomain.usecase.port.MemberRepository;
import com.MemberDomain.usecase.port.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

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
    }

    @Override
    public void insertNewUserBalance(String idUser) {
        balanceMapper.registerBalance(idUser);
    }

    @Override
    public LoginResponse getUserLoginData(String phoneNumber) {
        LoginResponse loginResponse = userMapper.getUserLoginData(phoneNumber);
        return loginResponse;
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

    @Override
    public void deleteOtp(String idUser) {
        otpMapper.deleteOtp(idUser);
    }

    @Override
    public OtpResponse matchOtp(String idUser, String otp) {
        OtpResponse otpResponse = otpMapper.matchOTP(idUser, otp);
        return otpResponse;
    }

    @Override
    public OtpResponse matchOtpDate(String idUser, String otp) {
        OtpResponse otpResponse = otpMapper.matchOTPDate(idUser, otp);
        return otpResponse;
    }

    @Override
    public void matchingOtp(String idUser) {
        otpMapper.mathcingOTP(idUser);
    }

    @Override
    public void unmatchingOtp(String idUser) {
        otpMapper.unmatchingOTP(idUser);
    }

    @Override
    public PasswordResponse getUserPassword(String idUser) {
        PasswordResponse passwordResponse = userMapper.getUserPassword(idUser);
        return passwordResponse;
    }

    @Override
    public void updatePassword(String idUser, String password) {
        userMapper.changePassword(idUser, password);
    }

    @Override
    public void updateName(String idUser, String name) {
        userMapper.changeName(idUser, name);
    }

    @Override
    public void updateEmail(String idUser, String email) {
        userMapper.changeEmail(idUser, email);
    }
}