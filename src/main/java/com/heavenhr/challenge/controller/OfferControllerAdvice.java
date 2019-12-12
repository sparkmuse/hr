package com.heavenhr.challenge.controller;

import com.heavenhr.challenge.exceptions.ApplicationNotFoundException;
import com.heavenhr.challenge.exceptions.EmailAlreadyExistsException;
import com.heavenhr.challenge.exceptions.OfferNotFoundException;
import com.heavenhr.challenge.exceptions.OfferTitleAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OfferControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = OfferNotFoundException.class)
    protected ResponseEntity<Object> handleOfferNotFoundException(OfferNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = OfferTitleAlreadyExistsException.class)
    protected ResponseEntity<Object> handleOfferTitleAlreadyExistsException(OfferTitleAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    protected ResponseEntity<Object> handleApplicationEmailAlreadyExists(EmailAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = ApplicationNotFoundException.class)
    protected ResponseEntity<Object> handleApplicationNotFoundException(ApplicationNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
