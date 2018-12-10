package com.home.ihm.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> notFoundException(final ResourceNotFoundException e) {
        return new ResponseEntity<>("Resource was not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> assertionException(final IllegalArgumentException e) {
        return new ResponseEntity<>("Resource was not found", HttpStatus.NOT_FOUND);
    }

    // all others
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception e) {
        log.error("Unhandled exception occurred", e);
        return new ResponseEntity<>("Unexpected problem occured", HttpStatus.NOT_FOUND);
    }
}