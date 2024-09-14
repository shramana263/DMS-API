package com.wish.dms_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wish.dms_api.dto.UserResponseDto;
import com.wish.dms_api.response.ResponseHandler;
import com.wish.dms_api.service.IUserService;

@RestController
public class UserController {

	@Autowired 
	private IUserService userService;
	
	@GetMapping
	public ResponseEntity<Object> index(){
		List<UserResponseDto> users= userService.getAllUser();
		return ResponseHandler.response(
				ResponseHandler.SUCCESS_CODE, 
				users,
				ResponseHandler.SUCCESS_MESSAGE
				);
	}
	
}
