package dev.amirgol.springtaskbackend.core.exception;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application
 * Handles various exceptions and returns appropriate error responses
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle validation errors from @Valid annotations
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        // Extract field errors
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        response.put("error", "Validation failed");
        response.put("details", errors);
        response.put("timestamp", System.currentTimeMillis());

        logger.error("Validation error: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle JWT related exceptions
     */
    @ExceptionHandler({JwtException.class, ExpiredJwtException.class})
    public ResponseEntity<Map<String, Object>> handleJwtExceptions(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid or expired token");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());

        logger.error("JWT error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handle authentication failures
     */
//    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
//    public ResponseEntity<Map<String, Object>> handleAuthenticationExceptions(Exception ex) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("error", "Authentication failed");
//        response.put("message", "Invalid credentials");
//        response.put("timestamp", System.currentTimeMillis());
//
//        logger.error("Authentication error: {}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }

    /**
     * Handle general runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Operation failed");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());

        logger.error("Runtime error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal server error");
        response.put("message", "An unexpected error occurred");
        response.put("timestamp", System.currentTimeMillis());

        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}