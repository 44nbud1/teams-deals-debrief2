package com.okta.examples.model.status.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GetProfileException extends ResponseStatusException {

    public GetProfileException(String message, HttpStatus status) {
        super(status,message);
        System.out.println(message);
    }
}
