package dev.amirgol.springtaskbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedValueException extends RuntimeException {
    public UnsupportedValueException(String fieldName, String value, Object supportedValues) {
        super(String.format(
                        "%s: '%s' is not supported. Supported values: %s ",
                        fieldName, value, supportedValues
                )
        );
    }
}
