package com.wish.dms_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DmsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApiApplication.class, args);
		System.out.println("http://localhost:8080");
	}

}
