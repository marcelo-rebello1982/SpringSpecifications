package com.example.demo.SpringProject.handler;


import com.example.demo.SpringProject.exception.EmployeeConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({EmployeeConflictException.class})
    public ResponseEntity<Object> handle(EmployeeConflictException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error Code ", ex.getErrorCode());
        errors.put("Error Message ", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            String fieldname = ((FieldError) error).getField();
                            String message = error.getDefaultMessage();
                            errors.put(fieldname, message);
                        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}