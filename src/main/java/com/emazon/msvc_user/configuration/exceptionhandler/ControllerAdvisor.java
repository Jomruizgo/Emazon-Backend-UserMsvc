package com.emazon.msvc_user.configuration.exceptionhandler;


import com.emazon.msvc_user.domain.exceptions.BadCredentialsException;
import com.emazon.msvc_user.domain.exceptions.DisabledAccountException;
import com.emazon.msvc_user.domain.exceptions.DuplicatedObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(DuplicatedObjectException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateCategoryNameException(DuplicatedObjectException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.CONFLICT.toString(), LocalDateTime.now()
        ));
    }

    @ExceptionHandler(DisabledAccountException.class)
    public ResponseEntity<ExceptionResponse> handleDisabledAccountException(DisabledAccountException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.FORBIDDEN.toString(), LocalDateTime.now()
        ));
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.UNAUTHORIZED.toString(), LocalDateTime.now()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                errors.values().toString().replace("[", "").replace("]", ""),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(ex.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred\n"+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
