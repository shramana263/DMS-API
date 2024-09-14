package com.wish.dms_api.service;

import com.wish.dms_api.dto.LoginDto;
import com.wish.dms_api.dto.LoginResponseDto;
import com.wish.dms_api.dto.RegisterDto;
import com.wish.dms_api.dto.UserResponseDto;

public interface IAuthService {

	UserResponseDto registerUser(RegisterDto registerDto);
	LoginResponseDto checkLogin(LoginDto loginDto);
}
