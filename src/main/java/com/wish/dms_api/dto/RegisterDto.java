package com.wish.dms_api.dto;

import com.wish.dms_api.validator.EmailExist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
	

	private String name;
	private String username;
	@EmailExist
	private String email;
	private String password;
}
