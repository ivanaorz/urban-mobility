package com.example.urbanmobility.exception;

import org.springframework.dao.DataAccessException;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message, DataAccessException ex) {
        super(message);
    }
}
