package com.amine.citiesapi.service.auth.authServiceImpl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amine.citiesapi.dto.VerifyCaptchaResponse;
import com.amine.citiesapi.service.auth.VerifyCaptchaService;

@Service
public class VerifyCaptchaServiceImpl implements VerifyCaptchaService {

	@Value("${verify_captcha_url}")
	private String captchaUrl;
	@Value("${captcha_secret_key}")
	private String captchaSecretKey;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public VerifyCaptchaResponse verifyCaptcha(String captcha) {
		URI verifyUri = URI.create(String.format(
				"https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s", captchaSecretKey, captcha));

		VerifyCaptchaResponse googleResponse = restTemplate().getForObject(verifyUri, VerifyCaptchaResponse.class);

		return googleResponse;
	}

}
