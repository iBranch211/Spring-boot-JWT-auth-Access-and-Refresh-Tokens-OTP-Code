package com.amine.citiesapi.service.auth;

import java.nio.file.AccessDeniedException;
import java.util.Map;

import com.amine.citiesapi.dto.LoginRequestDto;
import com.amine.citiesapi.dto.OtpVerificationRequestDto;
import com.amine.citiesapi.dto.RefreshTokenRequestDto;
import com.amine.citiesapi.dto.RegisterRequestDto;

public interface AuthService {
	
	public Map<String, Object> register(RegisterRequestDto request);
	public Map<String, Object> login(LoginRequestDto request) throws AccessDeniedException;
	public Map<String, Object> refreshToken(RefreshTokenRequestDto request);
	public Map<String, Object> verifyRegisterToken(String registerToken);
	public Map<String, Object> sendEmailVerifyLink(String username);
	public Map<String, Object> verifyOtpCode(OtpVerificationRequestDto request);
}
