package com.campusmanagement.exception.campus;

public class CampusNotFoundException extends RuntimeException{
    public CampusNotFoundException(String message) {
        super(message);
    }

    public CampusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
