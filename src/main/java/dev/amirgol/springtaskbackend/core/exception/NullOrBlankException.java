package dev.amirgol.springtaskbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullOrBlankException extends RuntimeException {
    public NullOrBlankException(String fieldName) {
        super(String.format("%s cannot be null or blank", fieldName));
    }
}