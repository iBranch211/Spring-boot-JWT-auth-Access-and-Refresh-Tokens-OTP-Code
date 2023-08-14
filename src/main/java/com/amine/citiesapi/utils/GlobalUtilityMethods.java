package com.amine.citiesapi.utils;

import java.util.Random;

public class GlobalUtilityMethods {
	
	public static String getSaltString(String saltChars, Integer len) {
        String SALTCHARS = saltChars;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
