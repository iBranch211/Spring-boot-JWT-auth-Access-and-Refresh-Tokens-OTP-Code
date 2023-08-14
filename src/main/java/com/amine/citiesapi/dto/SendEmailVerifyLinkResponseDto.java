package com.amine.citiesapi.dto;

public class SendEmailVerifyLinkResponseDto {
	
	private String message;
	
	public SendEmailVerifyLinkResponseDto() {}

	public SendEmailVerifyLinkResponseDto(String message) {
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
		return "SendEmailVerifyLinkResponseDto [message=" + message + "]";
	}
	
	
	
}
