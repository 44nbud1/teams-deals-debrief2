package com.okta.examples.adapter.status.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {

    public UnauthorizedException(String message, HttpStatus status) {
        super(status, message);
        System.out.println(message);
    }
}
