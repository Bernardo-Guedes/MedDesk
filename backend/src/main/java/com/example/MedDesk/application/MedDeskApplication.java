package com.example.MedDesk.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.MedDesk")
public class MedDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedDeskApplication.class, args);
	}

}
