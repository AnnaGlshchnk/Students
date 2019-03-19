package com.anna.controller.errors;

import org.springframework.http.HttpStatus;

public class ApiError {

  private HttpStatus status;
  private String message;

  ApiError(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ApiError{"
        + "status=" + status
        + ", message='" + message + '\''
        + '}';
  }
}