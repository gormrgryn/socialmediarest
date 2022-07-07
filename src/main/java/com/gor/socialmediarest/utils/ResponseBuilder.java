package com.gor.socialmediarest.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Component
public class ResponseBuilder {
    public ResponseEntity<?> buildResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", message);
        body.put("data", responseObj);

        return new ResponseEntity<>(body,status);
    }

    public ResponseEntity<?> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", message);

        return new ResponseEntity<>(body,status);
    }

    public ResponseEntity<?> buildResponse(String message, String errorMessage, HttpStatus status) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", message);
        body.put("error", errorMessage);

        return new ResponseEntity<>(body,status);
    }
}