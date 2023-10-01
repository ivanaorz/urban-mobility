package com.example.urbanmobility.handler;

import com.example.urbanmobility.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountCreationFailedException.class)
    public ResponseEntity<String> handleAccountCreationFailedException(AccountCreationFailedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500 internal server error
    }
    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<String> handleDatabaseConnectionException(DatabaseConnectionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500 internal server error
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); //404 not found status
    }
    @ExceptionHandler(InvalidCardNumberException.class)
    public ResponseEntity<String> handleInvalidCardNumberException(InvalidCardNumberException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //400 bad request
    }
    @ExceptionHandler(InvalidPermissionException.class)
    public ResponseEntity<String> handleInvalidPermissionException(InvalidPermissionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN); //403 forbidden
    }
    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<String> handleInvalidPhoneNumberException(InvalidPhoneNumberException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //400 bad request
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); //404 not found status
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); //409 conflict
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //400 bad request
    }




    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //400 bad request
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //400 bad request
    }
}
