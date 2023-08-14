package com.amine.citiesapi.controller.auth;

import java.nio.file.AccessDeniedException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amine.citiesapi.dto.LoginRequestDto;
import com.amine.citiesapi.dto.LoginResponseDto;
import com.amine.citiesapi.dto.OtpGenerationResponseDto;
import com.amine.citiesapi.dto.OtpVerificationRequestDto;
import com.amine.citiesapi.dto.RefreshTokenRequestDto;
import com.amine.citiesapi.dto.RefreshTokenResponseDto;
import com.amine.citiesapi.dto.RegisterRequestDto;
import com.amine.citiesapi.dto.RegisterResponseDto;
import com.amine.citiesapi.dto.SendEmailVerifyLinkRequestDto;
import com.amine.citiesapi.dto.SendEmailVerifyLinkResponseDto;
import com.amine.citiesapi.dto.VerifyRegisterTokenResponseDto;
import com.amine.citiesapi.service.auth.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {

		Map<String, Object> registerResponse = authService.register(request);

		RegisterResponseDto registerResponseDto = (RegisterResponseDto) registerResponse.get("registerResponseDto");
		HttpStatus responseStatus = (HttpStatus) registerResponse.get("status");

		return new ResponseEntity<>(registerResponseDto, responseStatus);

	}

	@PostMapping("/login")
	public ResponseEntity<OtpGenerationResponseDto> login(@Valid @RequestBody LoginRequestDto request) throws AccessDeniedException {

		Map<String, Object> loginResponse = authService.login(request);
		OtpGenerationResponseDto loginResponseDto = (OtpGenerationResponseDto) loginResponse.get("otpResponseDto");
		HttpStatus responseStatus = (HttpStatus) loginResponse.get("status");

		return new ResponseEntity<>(loginResponseDto, responseStatus);

	}

	@PostMapping("/refreshToken")
	public ResponseEntity<RefreshTokenResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenObject) {

		Map<String, Object> refreshResponse = authService.refreshToken(refreshTokenObject);
		RefreshTokenResponseDto refreshResponseDto = (RefreshTokenResponseDto) refreshResponse
				.get("refreshTokenResponseDto");
		HttpStatus responseStatus = (HttpStatus) refreshResponse.get("status");

		return new ResponseEntity<>(refreshResponseDto, responseStatus);
	}

	
	@GetMapping("/verify/register/account")	
	public ResponseEntity<VerifyRegisterTokenResponseDto> verifyRegisterToken(@RequestParam(required = true) String registerToken){
		
		Map<String, Object> verifyResponse = authService.verifyRegisterToken(registerToken);
		
		VerifyRegisterTokenResponseDto responseDto = (VerifyRegisterTokenResponseDto) verifyResponse.get("verifyRegisterTokenResponseDto");
		HttpStatus responseStatus = (HttpStatus) verifyResponse.get("status");
		
		return new ResponseEntity<>(responseDto, responseStatus);
		
	}
	
	@PostMapping("/send/account/verify/link")	
	public ResponseEntity<SendEmailVerifyLinkResponseDto> sendEmailVerifyLink(@Valid @RequestBody SendEmailVerifyLinkRequestDto sendVerifyDto){
		
		Map<String, Object> verifyResponse = authService.sendEmailVerifyLink(sendVerifyDto.getUsername());
		
		SendEmailVerifyLinkResponseDto responseDto = (SendEmailVerifyLinkResponseDto) verifyResponse.get("sendEmailVerifyLinkResponseDto");
		HttpStatus responseStatus = (HttpStatus) verifyResponse.get("status");
		
		return new ResponseEntity<>(responseDto, responseStatus);
		
	}
	
	@PostMapping("/verify/otp/account")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody OtpVerificationRequestDto request) {

		Map<String, Object> loginResponse = authService.verifyOtpCode(request);
		LoginResponseDto loginResponseDto = (LoginResponseDto) loginResponse.get("loginResponseDto");
		HttpStatus responseStatus = (HttpStatus) loginResponse.get("status");

		return new ResponseEntity<>(loginResponseDto, responseStatus);

	}
	
}
