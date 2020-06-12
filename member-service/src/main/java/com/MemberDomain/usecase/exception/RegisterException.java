package com.MemberDomain.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RegisterException extends ResponseStatusException {

    public RegisterException(String message, HttpStatus status){
        super(status, message);
    }
}

