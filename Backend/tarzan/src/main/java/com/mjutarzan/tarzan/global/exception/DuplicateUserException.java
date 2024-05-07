package com.mjutarzan.tarzan.global.exception;

public class DuplicateUserException  extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
