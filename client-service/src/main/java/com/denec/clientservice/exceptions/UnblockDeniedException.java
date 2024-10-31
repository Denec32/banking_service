package com.denec.clientservice.exceptions;

public class UnblockDeniedException extends RuntimeException {
    public UnblockDeniedException(String message) {
        super(message);
    }
    public UnblockDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
