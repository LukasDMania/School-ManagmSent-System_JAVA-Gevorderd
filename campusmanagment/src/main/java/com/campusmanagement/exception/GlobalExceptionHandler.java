package com.campusmanagement.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            Exception ex) {
        LOGGER.warning("Validation error: " + ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseExceptions(
            Exception ex) {
        LOGGER.severe("Database error occurred: " + ex.getMessage());
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "A database error occurred: " + ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex) {
        LOGGER.severe("Unexpected error occurred: " + ex.getMessage());
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred: " + ex.getMessage()
        );
    }
    private ResponseEntity<Map<String, Object>> createErrorResponse(
            HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }
}
