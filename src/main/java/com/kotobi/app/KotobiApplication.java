package com.kotobi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KotobiApplication {
	private String newUser;
	public static void main(String[] args) {
		SpringApplication.run(KotobiApplication.class, args);
	}

}
