package com.MemberDomain.usecase.port;

public interface UserRepository {
    Boolean doesEmailAvailable(String email);
    Boolean doesPhoneNumberAvailable(String phoneNumber);
}