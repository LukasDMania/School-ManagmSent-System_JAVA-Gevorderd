package com.campusmanagement.exception.campus;

public class InvalidCampusDataException extends RuntimeException {
    public InvalidCampusDataException(String message) {
        super(message);
    }

    public InvalidCampusDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
