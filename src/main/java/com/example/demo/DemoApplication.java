package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static final boolean DEBUG = true;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
