package com.example.urbanmobility.exception;

public class InvalidCardNumberException extends RuntimeException {
    public InvalidCardNumberException(String message) {
        super(message);
    }
}
