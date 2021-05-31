package com.example.caa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class CorrectAnAddress {

	public static void main(String[] args) {
		SpringApplication.run(CorrectAnAddress.class, args);
	}

}
