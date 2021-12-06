package com.example.demo.SpringProject.exception;

import lombok.Getter;

@Getter
public class EmployeeException extends RuntimeException {
  private final String message;
  private final String errorCode;

  protected EmployeeException(String message, String status) {
    super(message);
    this.message = message;
    this.errorCode = status;
  }

  protected EmployeeException(Throwable original, String status) {
    super(original);
    this.message = original.getMessage();
    this.errorCode = status;
  }
}