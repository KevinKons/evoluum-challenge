package com.kevinkons.location.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FormatNotSupportedException extends RuntimeException {
    public FormatNotSupportedException(String message) {
        super(message);
    }
}
