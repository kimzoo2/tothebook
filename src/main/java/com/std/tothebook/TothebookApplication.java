package com.std.tothebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TothebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TothebookApplication.class, args);
	}

}
