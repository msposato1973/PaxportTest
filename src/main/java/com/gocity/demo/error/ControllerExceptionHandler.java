package com.gocity.demo.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.gocity.demo.constant.Constants;
import com.gocity.demo.exception.CocityServiceException;
import com.gocity.demo.exception.ErrorMessage;
import com.gocity.demo.exception.NoDataFoundException;
import com.gocity.demo.exception.ResourceNotFoundException;


@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(NullPointerException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NO_CONTENT.value(),
        new Date(),
        Constants.ERROR_NULL,
        request.getDescription(false)
    );
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NO_CONTENT);
  }
  
  
  
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CocityServiceException.class)
  public ResponseEntity<ErrorMessage>  internalErrorHandler(CocityServiceException ex) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        Constants.ERROR_INTER,
        ex.getLocalizedMessage()
    );
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
  }
  
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoDataFoundException.class)
  public ResponseEntity<ErrorMessage>  internalErrorHandler(NoDataFoundException ex) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        Constants.ERROR_NO_DATA_FOUND,
        ex.getLocalizedMessage()
    );
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }
}