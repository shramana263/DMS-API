package com.wish.dms_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin
public class DmsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApiApplication.class, args);
		System.out.println("http://localhost:8080");
	}
	
	@GetMapping("/api/home")
	public ResponseEntity<String> home(){
		return new ResponseEntity<>("Hello home", HttpStatus.OK);
	}
	

}
