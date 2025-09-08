package dev.amirgol.springtaskbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
public class StorageException extends RuntimeException {
    public StorageException(String message, Exception e) {
        super(message);
    }
}
