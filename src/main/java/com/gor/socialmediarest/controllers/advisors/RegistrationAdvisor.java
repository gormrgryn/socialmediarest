package com.gor.socialmediarest.controllers.advisors;

import com.gor.socialmediarest.utils.ResponseBuilder;
import java.time.format.DateTimeParseException;
import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RegistrationAdvisor extends ResponseEntityExceptionHandler {
    @Autowired
    public ResponseBuilder responseBuilder;

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateTimeParseException(
        DateTimeParseException e, WebRequest request) {
        return responseBuilder.buildResponse(
            "Date must match 'yyyy-mm-dd'", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(
        IllegalArgumentException e, WebRequest request) {

        return responseBuilder.buildResponse(
            "One of request body values is invalid", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalArgumentException(
        IllegalStateException e, WebRequest request) {

        return responseBuilder.buildResponse(
            "Access is denied", e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(
        EntityNotFoundException e, WebRequest request) {
        return responseBuilder.buildResponse(
            "Entity not found", e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(
        RuntimeException e, WebRequest request) {
        return responseBuilder.buildResponse(
            "Run time error", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(
        IOException e, WebRequest request) {
        return responseBuilder.buildResponse(
            "IO error", e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
