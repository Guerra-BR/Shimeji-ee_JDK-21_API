package com.group_finity.mascot.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -2439506442277971186L;

    public BadRequestException(String message) {
        super(message);
    }
}
