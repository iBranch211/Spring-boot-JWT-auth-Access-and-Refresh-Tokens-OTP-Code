package com.amine.citiesapi.exceptions;

public class UserAlreadyVerifiedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	
	public UserAlreadyVerifiedException(String message) {
		super(message);
	}

}
