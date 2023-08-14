package com.amine.citiesapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SendEmailVerifyLinkRequestDto {
	
	@NotNull
	@NotBlank(message = "Username can't be blank")
	private String username;
	
	
	public SendEmailVerifyLinkRequestDto() {}


	public SendEmailVerifyLinkRequestDto(@NotNull @NotBlank(message = "Username can't be blank") String username) {
		super();
		this.username = username;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		return "SendEmailVerifyLinkRequestDto [username=" + username + "]";
	}
	
	
	
	
}
