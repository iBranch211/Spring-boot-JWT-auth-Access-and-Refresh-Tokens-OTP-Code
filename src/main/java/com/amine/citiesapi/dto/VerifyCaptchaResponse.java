package com.amine.citiesapi.dto;

public class VerifyCaptchaResponse {
	
	private Boolean success;
	
	public VerifyCaptchaResponse() {}

	public VerifyCaptchaResponse(Boolean success) {
		super();
		this.success = success;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "VerifyCaptchaResponse [success=" + success + "]";
	}
	
	

}
