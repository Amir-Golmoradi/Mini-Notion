package dev.amirgol.springtaskbackend.core.exception;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application
 * Handles various exceptions and returns appropriate error responses
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


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

        log.error("Validation error: {}", errors);
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

        log.error("JWT error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({MinioException.class, StorageException.class, IOException.class})
    public ResponseEntity<Map<String, Object>> handleMinioExceptions(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Storage error");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        log.error("Storage error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    /**
     * Handle general runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Operation failed");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());

        log.error("Runtime error: {}", ex.getMessage(), ex);
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

        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(UnsupportedValueException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedValueException(UnsupportedValueException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Unsupported value");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        log.error("Unsupported value error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Resource already exists");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        log.error("Resource already exists error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Resource not found");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        log.error("Resource not found error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(LengthOutOfBoundsException.class)
    public ResponseEntity<Map<String, Object>> handleLengthOutOfBoundsException(LengthOutOfBoundsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid length");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        log.error("Invalid length error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NullOrBlankException.class)
    public ResponseEntity<Map<String, Object>> handleNullOrBlankException(NullOrBlankException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("errorCode", "VALIDATION_ERROR");
        response.put("errorType", "NullOrBlankError");
        response.put("message", String.format("Validation failed: %s", ex.getMessage()));
        response.put("timestamp", System.currentTimeMillis());
        log.error("Validation error - Null or blank value: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidCharactersException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCharactersException(InvalidCharactersException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("errorCode", "VALIDATION_ERROR");
        response.put("errorType", "InvalidCharactersError");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        log.error("Invalid characters error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}