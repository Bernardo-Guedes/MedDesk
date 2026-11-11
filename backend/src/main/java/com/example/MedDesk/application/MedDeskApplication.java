package com.example.MedDesk.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.MedDesk")
@EntityScan("com.example.MedDesk.model")
@EnableJpaRepositories("com.example.MedDesk.repository")
public class MedDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedDeskApplication.class, args);
	}

}
