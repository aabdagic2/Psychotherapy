package com.management.user.exceptions;

public class InvalidFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidFormatException(String message) {
        super(message);
    }
}
