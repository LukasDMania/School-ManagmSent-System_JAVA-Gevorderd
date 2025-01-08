package com.campusmanagement.exception.lokaal;

public class InvalidLokaalDataException extends RuntimeException {
    public InvalidLokaalDataException(String message) {
        super(message);
    }

    public InvalidLokaalDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
