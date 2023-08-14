package com.amine.citiesapi.dto;

public class VerifyRegisterTokenResponseDto {
	
	private String message;
	
	public VerifyRegisterTokenResponseDto() {}
	

	public VerifyRegisterTokenResponseDto(String message) {
		super();
		this.message = message;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "VerifyRegisterTokenResponseDto [message=" + message + "]";
	}
	
	
	
}
