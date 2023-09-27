package com.example.urbanmobility.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class AccountCreationFailedException extends RuntimeException {
    public AccountCreationFailedException(String message) {
        super(message);
    }
}
