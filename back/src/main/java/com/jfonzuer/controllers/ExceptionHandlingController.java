package com.jfonzuer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by pgm on 07/11/16.
 */
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(value = { IllegalStateException.class, ResourceNotFoundException.class })
    public ResponseEntity<Object> authenticationError(RuntimeException ex, WebRequest request) {

        ex.printStackTrace();

        String message = ex.getMessage();
        System.out.println(ex.getMessage());

        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
