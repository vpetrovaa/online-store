package com.solvd.onlinestore.domain.exception;

public class AuthenticationException extends org.springframework.security.core.AuthenticationException {

    public AuthenticationException(String message) {
        super(message);
    }

}
