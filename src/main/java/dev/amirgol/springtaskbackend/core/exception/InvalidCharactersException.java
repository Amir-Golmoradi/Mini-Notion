package dev.amirgol.springtaskbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCharactersException extends RuntimeException {
    public InvalidCharactersException(String fileName) {
        super(String.format("%s contains invalid characters", fileName));
    }
}
