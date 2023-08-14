package com.amine.citiesapi.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleAllExceptions(Exception ex, WebRequest request){
		ErrorObject errorObject =  new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorObject> handleBadRequestExceptions(MethodArgumentNotValidException ex, WebRequest request){
		ErrorObject errorObject =  new ErrorObject(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<ErrorObject> handleCityNotFoundException(CityNotFoundException ex, WebRequest request){
		ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OtpInvalidException.class)
	public ResponseEntity<ErrorObject> handleOtpInvalidException(OtpInvalidException ex, WebRequest request){
		ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorObject> handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
		ErrorObject errorObject = new ErrorObject(HttpStatus.FORBIDDEN.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UserAuthenticationException.class)
	public ResponseEntity<ErrorObject> handleUserAuthException(UserAuthenticationException ex, WebRequest request){
		ErrorObject errorObject = new ErrorObject(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	public ResponseEntity<ErrorObject> handleAuthCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex, WebRequest request){
		ErrorObject errorObject = new ErrorObject(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.UNAUTHORIZED);
	}
	
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorObject> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorObject, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.BAD_REQUEST.value(), "Malformed request body", LocalDateTime.now());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorObject> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_ACCEPTABLE.value(), "Unsupported media type", LocalDateTime.now());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_ACCEPTABLE);
    }
	

}
