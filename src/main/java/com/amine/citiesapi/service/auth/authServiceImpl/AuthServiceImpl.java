package com.amine.citiesapi.service.auth.authServiceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.amine.citiesapi.dao.auth.AuthUserRepository;
import com.amine.citiesapi.dao.auth.RoleRepository;
import com.amine.citiesapi.dto.LoginRequestDto;
import com.amine.citiesapi.dto.LoginResponseDto;
import com.amine.citiesapi.dto.OtpGenerationResponseDto;
import com.amine.citiesapi.dto.OtpVerificationRequestDto;
import com.amine.citiesapi.dto.RefreshTokenRequestDto;
import com.amine.citiesapi.dto.RefreshTokenResponseDto;
import com.amine.citiesapi.dto.RegisterRequestDto;
import com.amine.citiesapi.dto.RegisterResponseDto;
import com.amine.citiesapi.dto.SendEmailVerifyLinkResponseDto;
import com.amine.citiesapi.dto.VerifyRegisterTokenResponseDto;
import com.amine.citiesapi.entities.auth.AuthUser;
import com.amine.citiesapi.entities.auth.Role;
import com.amine.citiesapi.exceptions.OtpInvalidException;
import com.amine.citiesapi.exceptions.UserAuthenticationException;
import com.amine.citiesapi.mail.EmailJwtTokenUtils;
import com.amine.citiesapi.mail.MailService;
import com.amine.citiesapi.otp.OtpJwtTokenUtils;
import com.amine.citiesapi.security.JwtRefreshTokenUtils;
import com.amine.citiesapi.security.JwtTokenUtils;
import com.amine.citiesapi.service.auth.AuthService;
import com.amine.citiesapi.utils.GlobalUtilityMethods;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Value("${base_domain}")
	private String baseDomain;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthUserRepository authUserRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenUtils jwtTokenGenerator;
	@Autowired
	private JwtRefreshTokenUtils jwtRefreshTokenGenerator;
	@Autowired
	private MailService mailService;
	@Autowired
	private EmailJwtTokenUtils emailJwtTokenUtils;
	@Autowired
	private OtpJwtTokenUtils otpJwtTokenUtils;

	public Map<String, Object> register(RegisterRequestDto request) {
		HashMap<String, Object> responseMap = new HashMap<>();
		if (authUserRepository.existsByUsername(request.getUsername())) {

			responseMap.put("registerResponseDto", new RegisterResponseDto("This user already exists"));
			responseMap.put("status", HttpStatus.BAD_REQUEST);

			return responseMap;
		}

		AuthUser authUser = new AuthUser();
		authUser.setFirstname(request.getFirstname());
		authUser.setLastname(request.getLastname());
		authUser.setUsername(request.getUsername());
		authUser.setPassword(passwordEncoder.encode(request.getPassword()));

		Role role = roleRepository.findByName("USER").get();

		authUser.setRoles(Collections.singletonList(role));

		authUserRepository.save(authUser);

		responseMap.put("registerResponseDto", new RegisterResponseDto("User added successfully"));
		responseMap.put("status", HttpStatus.CREATED);

		String registrationToken = emailJwtTokenUtils.generateToken(request.getUsername());
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<h3>Verify your account below<h3>");
		emailBody.append("<div><a href='" + baseDomain + "/api/auth/verify/register/account?registerToken="
				+ registrationToken + "'>Click here to verify your account !</a></div>");
		try {
			mailService.sendHtmlEmail(request.getUsername(), "Verify your account", emailBody.toString());
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return responseMap;
	}

	public Map<String, Object> login(LoginRequestDto request) throws AccessDeniedException {

		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}catch(Exception e) {
			throw new  UserAuthenticationException("Bad credentials");
		}

		Optional<AuthUser> authUser = authUserRepository.findByUsername(request.getUsername());
		if (authUser.isPresent()) {
			if (authUser.get().getVerified() == false) {
				throw new AccessDeniedException("Access Denied. Please verify your account");
			} else {
				String otpCode = GlobalUtilityMethods.getSaltString("0123456789", 6);
				StringBuilder emailBody = new StringBuilder();
				emailBody.append("<h3>OTP code<h3>");
				emailBody.append("<div><h3>Enter the following OTP code : </h3><h2>" + otpCode + "</h2></div>");
				try {
					mailService.sendHtmlEmail(request.getUsername(), "Verify your account", emailBody.toString());
					authUser.get().setOtp(otpCode);
					authUserRepository.save(authUser.get());
					String otpJwtToken = otpJwtTokenUtils.generateToken(authUser.get().getUsername());
					HashMap<String, Object> responseMap = new HashMap<>();
					responseMap.put("otpResponseDto",
							new OtpGenerationResponseDto("A message was sent to you containing your OTP authentication code", otpJwtToken));
					responseMap.put("status", HttpStatus.OK);

					return responseMap;
				} catch (MessagingException e) {
					throw new RuntimeException("An internal error occured");
				}
			}
		} else {
			throw new UsernameNotFoundException("User can't be found");
		}

	}

	public Map<String, Object> refreshToken(RefreshTokenRequestDto request) {

		if (!jwtRefreshTokenGenerator.validateToken(request.getRefreshToken())) {
			throw new AuthenticationCredentialsNotFoundException("Token was expired or incorrect");
		}

		String username = jwtRefreshTokenGenerator.getUsernameFromJwt(request.getRefreshToken());
		String accessToken = jwtTokenGenerator.generateToken(username);

		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("refreshTokenResponseDto",
				new RefreshTokenResponseDto("Authenticated successfully", accessToken));
		responseMap.put("status", HttpStatus.OK);

		return responseMap;
	}

	public Map<String, Object> verifyRegisterToken(String registerToken) {
		if (emailJwtTokenUtils.validateToken(registerToken)) {
			String username = emailJwtTokenUtils.getUsernameFromJwt(registerToken);
			Optional<AuthUser> user = authUserRepository.findByUsername(username);

			if (user.isPresent()) {
				user.get().setVerified(true);
				authUserRepository.save(user.get());

				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("verifyRegisterTokenResponseDto", new VerifyRegisterTokenResponseDto(
						user.get().getFirstname() + " your account has been successfully verified"));
				responseMap.put("status", HttpStatus.OK);
				StringBuilder emailBody = new StringBuilder();
				emailBody.append(
						"<h3>" + user.get().getFirstname() + ", Your account has been successfully verified !<h3>");
				try {
					mailService.sendHtmlEmail(user.get().getUsername(), "Account verified !", emailBody.toString());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				return responseMap;
			} else {
				throw new UsernameNotFoundException("User cannot be found");
			}

		} else {
			throw new AuthenticationCredentialsNotFoundException("Registration Token is expired or incorrect");
		}
	}

	public Map<String, Object> sendEmailVerifyLink(String username) {
		Optional<AuthUser> user = authUserRepository.findByUsername(username);
		if (user.isPresent()) {
			if (!user.get().getVerified()) {
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("sendEmailVerifyLinkResponseDto",
						new SendEmailVerifyLinkResponseDto("A verification email has been sent to your email address"));
				responseMap.put("status", HttpStatus.OK);

				String registrationToken = emailJwtTokenUtils.generateToken(user.get().getUsername());
				StringBuilder emailBody = new StringBuilder();
				emailBody.append("<h3>Verify your account below<h3>");
				emailBody.append(
						"<div><a href='" + baseDomain + "/api/auth/verify/register/account?registerToken="
								+ registrationToken + "'>Click here to verify your account !</a></div>");
				try {
					mailService.sendHtmlEmail(user.get().getUsername(), "Verify your account", emailBody.toString());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				return responseMap;
			} else {
				throw new ResponseStatusException(HttpStatus.ACCEPTED, "This account is already verified");
			}
		} else {
			throw new UsernameNotFoundException("This user cannot be found");
		}
	}

	public Map<String, Object> verifyOtpCode(OtpVerificationRequestDto otpVerificationRequestDto) {
		String otpJwtToken = otpVerificationRequestDto.getToken();
		String otp = otpVerificationRequestDto.getOtp();
		if(otpJwtTokenUtils.validateToken(otpJwtToken)) {
			String username = otpJwtTokenUtils.getUsernameFromJwt(otpJwtToken);
			Optional<AuthUser> user = authUserRepository.findByUsername(username);
			if(user.isPresent()) {
				if(user.get().getOtp().equals(otp)) {
				
					String accessToken = jwtTokenGenerator.generateToken(user.get().getUsername());
					String refreshToken = jwtRefreshTokenGenerator.generateToken(user.get().getUsername());

					HashMap<String, Object> responseMap = new HashMap<>();

					responseMap.put("loginResponseDto", new LoginResponseDto("Authenticated successfully", accessToken, refreshToken));
					responseMap.put("status", HttpStatus.OK);

					return responseMap;
				}else {
					throw new OtpInvalidException("OTP code is not valid");
				}
			}else {
				throw new OtpInvalidException("OTP code is not valid");
			}
		}else {
			throw new OtpInvalidException("OTP code has expired. Please login again.");
		}
		
	}

}







