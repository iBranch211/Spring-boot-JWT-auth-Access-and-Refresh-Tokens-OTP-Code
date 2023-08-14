package com.amine.citiesapi.dto;

public class OtpGenerationResponseDto {
	
	private String message;
	private String otpVerificationToken;

	public OtpGenerationResponseDto() {}
	
	public OtpGenerationResponseDto(String message, String otpVerificationToken) {
		super();
		this.message = message;
		this.otpVerificationToken = otpVerificationToken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

	public String getOtpVerificationToken() {
		return otpVerificationToken;
	}

	public void setOtpVerificationToken(String otpVerificationToken) {
		this.otpVerificationToken = otpVerificationToken;
	}

	@Override
	public String toString() {
		return "OtpResponseDto [message=" + message + ", otpVerificationToken=" + otpVerificationToken + "]";
	}

	
	

}
