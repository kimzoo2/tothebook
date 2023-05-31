package com.std.tothebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TothebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TothebookApplication.class, args);
	}

}
