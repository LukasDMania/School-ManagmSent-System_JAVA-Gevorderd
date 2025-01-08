package com.campusmanagement.exception.reservatie;

public class ReservatieNotFoundException extends RuntimeException{
    public ReservatieNotFoundException(String message) {
        super(message);
    }

    public ReservatieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
