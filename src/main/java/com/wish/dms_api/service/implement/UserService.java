package com.wish.dms_api.service.implement;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wish.dms_api.dto.UserResponseDto;
import com.wish.dms_api.entity.User;
import com.wish.dms_api.repository.IUserRepository;
import com.wish.dms_api.service.IUserService;
@Service
public class UserService implements IUserService{

	@Autowired IUserRepository userRepository;
	@Autowired ModelMapper modelMapper;
	
	@Override
	public boolean emailExists(String email) {
		Optional<User> user=userRepository.findByEmail(email);
//		return userRepository.findByEmail(email);
		return user.isEmpty();
	}

	@Override
	public List<UserResponseDto> getAllUser() {
		List<User> users= userRepository.findAll();
		for(User user: users) {
			modelMapper.map(users, UserResponseDto.class);
		}
		return null;
	}

}
