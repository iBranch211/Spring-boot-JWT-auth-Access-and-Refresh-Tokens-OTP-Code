package com.amine.citiesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class CitiesApiApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(CitiesApiApplication.class, args);
	}
	
	
}


