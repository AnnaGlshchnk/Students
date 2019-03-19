package com.anna.controller.errors;

import com.anna.exception.OperationFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@CrossOrigin
@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

  /**
   * Exception Handler for INTERNAL_SERVER_ERROR.
   */
  @ExceptionHandler(value = {DataAccessException.class})
  public ResponseEntity<Object> handleDataAccessException(DataAccessException ex,
      WebRequest request) {
    final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
        ex.getLocalizedMessage());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  /**
   * Exception Handler for BAD_REQUEST.
   */
  @ExceptionHandler(value = IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalException(IllegalArgumentException ex,
      WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Exception Handler for BAD_REQUEST.
   */
  @ExceptionHandler(value = IllegalStateException.class)
  public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex,
      WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Exception Handler for NOT_FOUND.
   */
  @ExceptionHandler(value = {EmptyResultDataAccessException.class})
  public ResponseEntity<Object> handleEmptyResultDataAccessException(
      EmptyResultDataAccessException ex, WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  /**
   * Exception Handler for BAD_REQUEST.
   */
  @ExceptionHandler(value = {OperationFailedException.class})
  public ResponseEntity<Object> handleOperationFailedException(OperationFailedException ex,
      WebRequest request) {
    final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }
}
