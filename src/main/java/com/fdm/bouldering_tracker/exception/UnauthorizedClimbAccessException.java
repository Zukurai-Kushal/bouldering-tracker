package com.fdm.bouldering_tracker.exception;

public class UnauthorizedClimbAccessException extends RuntimeException {
    public UnauthorizedClimbAccessException(String message) {
        super(message);
    }
}