package dev.amirgol.springtaskbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LengthOutOfBoundsException extends RuntimeException {
    public LengthOutOfBoundsException(String fieldName, int min, int max) {
        super(String.format(
                "%s length must be between %d and %d characters",
                fieldName, min, max
        ));
    }
}
