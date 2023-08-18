package com.amine.citiesapi.exceptions;

public class InvalidRecaptchaTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidRecaptchaTokenException(String message) {
		super(message);
	}

}
