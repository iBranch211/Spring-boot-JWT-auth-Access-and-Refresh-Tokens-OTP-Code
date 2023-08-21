package com.amine.citiesapi.dto;

import java.util.List;

import com.amine.citiesapi.entities.auth.Role;

public class LoginResponseDto {
	
	private String message;
	private String accessToken;
	private String refreshToken;
	private List<Role> roles;
	
	public LoginResponseDto() {}
	
	public LoginResponseDto(String message, String accessToken, String refreshToken, List<Role> roles) {
		this.message = message;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.roles = roles;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "LoginResponseDto [message=" + message + ", accessToken=" + accessToken + ", refreshToken="
				+ refreshToken + ", roles=" + roles + "]";
	}





	
}
