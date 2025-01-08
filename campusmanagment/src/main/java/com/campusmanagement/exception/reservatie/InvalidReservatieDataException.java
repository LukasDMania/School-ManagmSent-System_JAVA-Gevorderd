package com.campusmanagement.exception.reservatie;

public class InvalidReservatieDataException extends RuntimeException {
    public InvalidReservatieDataException(String message) {
        super(message);
    }

    public InvalidReservatieDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
