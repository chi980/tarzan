package com.mjutarzan.tarzan.global.exception;

public class RequiredParameterMissingException extends RuntimeException {
    public RequiredParameterMissingException(String message) {
        super(message);
    }
}