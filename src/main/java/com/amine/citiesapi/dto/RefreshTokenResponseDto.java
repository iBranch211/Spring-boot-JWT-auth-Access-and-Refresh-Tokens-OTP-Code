package com.amine.citiesapi.dto;

import java.util.List;

import com.amine.citiesapi.entities.auth.Role;

public class RefreshTokenResponseDto {
	
	private String message;
	private String accessToken;
	private List<Role> roles;
	
	public RefreshTokenResponseDto() {}

	public RefreshTokenResponseDto(String message, String accessToken, List<Role> roles) {
		super();
		this.message = message;
		this.accessToken = accessToken;
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
	
	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "RefreshTokenResponseDto [message=" + message + ", accessToken=" + accessToken + ", roles=" + roles
				+ "]";
	}

	
	

}
