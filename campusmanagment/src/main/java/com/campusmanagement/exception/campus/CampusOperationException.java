package com.campusmanagement.exception.campus;

public class CampusOperationException extends RuntimeException {
    public CampusOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}