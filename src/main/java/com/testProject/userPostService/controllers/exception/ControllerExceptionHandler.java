package com.testProject.userPostService.controllers.exception;

import com.testProject.userPostService.controllers.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;


import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice(annotations = CustomExceptionHandler.class)
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = Arrays.asList(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                details);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidIdException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidIdException(InvalidIdException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                Arrays.asList("Invalid ID provided"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPostException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidPostException(InvalidPostException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                Arrays.asList("Invalid Post provided"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidUserException(InvalidUserException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                Arrays.asList("Invalid User provided"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
