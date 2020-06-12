package com.MemberDomain.adapter.repository;

import com.MemberDomain.usecase.port.UserRepository;
import com.MemberDomain.usecase.port.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UserMapper userMapper;

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
}
