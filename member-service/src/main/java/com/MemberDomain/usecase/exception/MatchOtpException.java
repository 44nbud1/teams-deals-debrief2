package com.MemberDomain.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MatchOtpException extends ResponseStatusException {

    public MatchOtpException(String message, HttpStatus status){
        super(status, message);
    }
}