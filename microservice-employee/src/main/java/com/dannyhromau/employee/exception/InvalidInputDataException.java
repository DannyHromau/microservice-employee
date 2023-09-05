package com.dannyhromau.employee.exception;

public class InvalidInputDataException extends IllegalArgumentException {
    public InvalidInputDataException(String message) {
        super(message);
    }
}
