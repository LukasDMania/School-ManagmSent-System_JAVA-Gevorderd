package com.campusmanagement.exception.lokaal;

public class LokaalNotFoundException extends RuntimeException{
    public LokaalNotFoundException(String message) {
        super(message);
    }

    public LokaalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
