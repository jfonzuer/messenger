package com.jfonzuer.controllers;

import com.jfonzuer.dto.CustomErrorType;
import com.jfonzuer.dto.response.ExceptionDto;
import com.jfonzuer.exception.AccountNotActivatedException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by pgm on 07/11/16.
 */
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(value = {IllegalStateException.class, ResourceNotFoundException.class })
    public ResponseEntity<Object> authenticationError(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        String message = ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { AccountNotActivatedException.class })
    public ResponseEntity<Object> uploadError(RuntimeException ex, WebRequest request) {
        // TODO use logger
        ex.printStackTrace();
        String message = ex.getMessage();
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, new ExceptionDto(message), headers, HttpStatus.LOCKED, request);
    }

    @ExceptionHandler(value = { FileUploadBase.FileSizeLimitExceededException.class })
    ResponseEntity<?> uploadError(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(new CustomErrorType(status.value(), ex.getMessage()), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
