package com.mjutarzan.tarzan.global.common.exception;

public class RequiredParameterMissingException extends RuntimeException {
    public RequiredParameterMissingException(String message) {
        super(message);
    }
}