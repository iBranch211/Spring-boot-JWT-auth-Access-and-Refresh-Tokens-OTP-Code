package com.amine.citiesapi.otp;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import com.amine.citiesapi.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class OtpJwtTokenUtils {
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String generateToken(String username) {
		Date currentDate = new Date();
		Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_OTP_EXPIRATION);

		String token = Jwts.builder().setSubject(username).setIssuedAt(currentDate).setExpiration(expiryDate)
				.signWith(key, SignatureAlgorithm.HS512).compact();

		return token;
	}

	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("OTP Token is expired or incorrect");
		}
	}
}
