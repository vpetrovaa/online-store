package com.solvd.onlinestore.domain.exception;

public class WrongOrderingException extends RuntimeException {

    public WrongOrderingException(String message) {
        super(message);
    }

}
