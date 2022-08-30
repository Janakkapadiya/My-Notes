package com.NoteTaker.java.Note.taker.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NoteTakerWebappApplication {
	public static void main(String[] args) {
		SpringApplication.run(NoteTakerWebappApplication.class, args);
	}
}
