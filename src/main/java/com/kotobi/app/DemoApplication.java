package com.kotobi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	private String newUser;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
