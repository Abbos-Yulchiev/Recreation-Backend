package com.epam.recreation_module.exception;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler implements ResponseErrorHandler {

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handlerNullPointerException(NullPointerException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<?> handlerTypeMismatchException(TypeMismatchException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> handlerUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ClassCastException.class})
    public ResponseEntity<Object> handlerClassCastException(ClassCastException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MethodNotAllowedException.class})
    public ResponseEntity<Object> handlerMethodNotAllowedException(MethodNotAllowedException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, LocalDateTime.now()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<Object> handlerIOException(IOException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {WebClientException.class})
    public ResponseEntity<Object> handlerWebClientException(WebClientException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, LocalDateTime.now()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException("Citizen Id found!", 10140L);
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
    }
}
