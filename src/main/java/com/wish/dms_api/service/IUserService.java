package com.wish.dms_api.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.wish.dms_api.dto.UserResponseDto;

public interface IUserService {
	
	UserDetailsService userDetailsService();

	boolean emailExists(String email);

	List<UserResponseDto> getAllUser();

	UserResponseDto getCurrentUser();

}
