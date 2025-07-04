package com.java.hash.utils.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.java.hash.domain.utils.HashException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HashException.class)
    public ResponseEntity<ErrorDTO> handleHashException(HashException ex) {
        ErrorDTO error = new ErrorDTO();

        error.setError(ex.getMessage());
        error.setErrorDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        error.setHttpCodeMessage(HttpStatus.BAD_REQUEST.toString());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {

        ErrorDTO error = new ErrorDTO();

        error.setError(ex.getMessage());
        error.setErrorDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        error.setHttpCodeMessage(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}