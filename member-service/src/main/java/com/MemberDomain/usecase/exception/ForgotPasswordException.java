package com.MemberDomain.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForgotPasswordException extends ResponseStatusException {

    public ForgotPasswordException(String message, HttpStatus status){
        super(status, message);
    }
}
