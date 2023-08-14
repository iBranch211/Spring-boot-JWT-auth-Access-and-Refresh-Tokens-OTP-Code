package com.amine.citiesapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OtpVerificationRequestDto {
	@NotNull
	@NotBlank(message = "Otp code can't be blank")
	private String otp;
	@NotNull
	@NotBlank(message = "Token can't be blank")
	private String token;
	
	public OtpVerificationRequestDto() {}

	public OtpVerificationRequestDto(String otp, String token) {
		super();
		this.otp = otp;
		this.token = token;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "OtpRequestDto [otp=" + otp + ", token=" + token + "]";
	}
	
	
	
}
