package com.wish.dms_api.service.implement;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wish.dms_api.dto.LoginDto;
import com.wish.dms_api.dto.LoginResponseDto;
import com.wish.dms_api.dto.RefreshTokenDto;
import com.wish.dms_api.dto.RegisterDto;
import com.wish.dms_api.dto.UserResponseDto;
import com.wish.dms_api.entity.User;
import com.wish.dms_api.repository.IUserRepository;
import com.wish.dms_api.security.JwtService;
import com.wish.dms_api.service.IAuthService;

import jakarta.validation.Valid;

@Service
public class AuthService implements IAuthService{

	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired BCryptPasswordEncoder passwordEncoder;
	
	@Autowired AuthenticationManager authenticationManager;
	
	@Autowired JwtService jwtService;
	
	@Autowired LoginResponseDto loginResponseDto;
	
	@Override
	public UserResponseDto registerUser(RegisterDto registerDto) {
		registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		User user = mapper.map(registerDto, User.class);
		userRepository.save(user);
		return mapper.map(user, UserResponseDto.class);
	}

	@Override
	public LoginResponseDto checkLogin(LoginDto loginDto) {

		try {
			
			Authentication authentication= authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
					);
			
			if(authentication.isAuthenticated()) {
				String token= jwtService.generateToken(loginDto.getUsername());
				String refreshToken= jwtService.generateRefreshToken(loginDto.getUsername());
				
				
//			LoginResponseDto loginResponseDto;
				loginResponseDto.setToken(token);
				loginResponseDto.setRefreshToken(refreshToken);
				User user= userRepository.findByUsername(loginDto.getUsername());
				loginResponseDto.setData(mapper.map(user,UserResponseDto.class));
				
				return loginResponseDto;
			}
			
			throw new UsernameNotFoundException("Username not found");
		} catch(Exception ex){
			throw new NullPointerException();
		}

	}

	@Override
	public LoginResponseDto refreshToken(@Valid RefreshTokenDto refreshTokenDto) {


		String username= jwtService.extractUsername(refreshTokenDto.getRefreshToken());
		User user= userRepository.findByUsername(username);
		if(jwtService.validateToken(refreshTokenDto.getRefreshToken(), user)) {
			
			String token= jwtService.generateToken(username);
			String refreshToken=jwtService.generateRefreshToken(username);
			loginResponseDto.setToken(token);
			loginResponseDto.setRefreshToken(refreshToken);
			loginResponseDto.setData(mapper.map(user, UserResponseDto.class));
			return loginResponseDto;
			
		}
		return null;
		
		
	}

}
