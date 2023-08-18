package com.amine.citiesapi.service.auth;

import com.amine.citiesapi.dto.VerifyCaptchaResponse;

public interface VerifyCaptchaService {

	public VerifyCaptchaResponse verifyCaptcha(String captcha);
	
}
