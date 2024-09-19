package com.wish.dms_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wish.dms_api.dto.LoginDto;
import com.wish.dms_api.dto.LoginResponseDto;
import com.wish.dms_api.dto.RefreshTokenDto;
import com.wish.dms_api.dto.RegisterDto;
import com.wish.dms_api.dto.UserResponseDto;
import com.wish.dms_api.response.ResponseHandler;
import com.wish.dms_api.service.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth" , description = "Auth API")
public class AuthController {

	@Autowired
	private IAuthService authService;
	
	@Operation(summary = "Register new user" , description = "Register new user")
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerdto){
		System.out.println(registerdto);
		
		UserResponseDto registeredUser= authService.registerUser(registerdto);
//		return new ResponseEntity<>(registeredUser, HttpStatus.OK);
		
		return ResponseHandler.response(
				ResponseHandler.SUCCESS_CODE,
				ResponseHandler.SUCCESS_MESSAGE
				);
				
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> register(@Valid @RequestBody LoginDto loginDto){
		
//		System.out.println(loginDto);
		LoginResponseDto loginResponseDto= authService.checkLogin(loginDto);

//		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
		return ResponseHandler.response(
				ResponseHandler.SUCCESS_CODE,
				loginResponseDto,
				ResponseHandler.SUCCESS_MESSAGE
				);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody @Valid RefreshTokenDto refreshTokenDto){
		LoginResponseDto loginResponseDto= authService.refreshToken(refreshTokenDto);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}
}

