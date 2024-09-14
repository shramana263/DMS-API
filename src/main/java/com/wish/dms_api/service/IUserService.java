package com.wish.dms_api.service;

import java.util.List;

import com.wish.dms_api.dto.UserResponseDto;

public interface IUserService {

	boolean emailExists(String email);

	List<UserResponseDto> getAllUser();

}
