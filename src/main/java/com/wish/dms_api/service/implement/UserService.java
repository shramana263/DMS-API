package com.wish.dms_api.service.implement;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wish.dms_api.dto.UserResponseDto;
import com.wish.dms_api.entity.User;
import com.wish.dms_api.repository.IUserRepository;
import com.wish.dms_api.service.IUserService;
@Service
public class UserService implements IUserService{

	@Autowired IUserRepository userRepository;
	@Autowired ModelMapper mapper;
	
	@Override
	public boolean emailExists(String email) {
		Optional<User> user=userRepository.findByEmail(email);
//		return userRepository.findByEmail(email);
		return user.isEmpty();
	}

	@Override
	public List<UserResponseDto> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public UserResponseDto getCurrentUser() {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'getCurrentUser'");
	// }

	@Override
	public UserResponseDto getCurrentUser() {
//		UserSingleton currentUserDetails = (UserSingleton)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUserDetails= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user= userRepository.findById(currentUserDetails.getId()).orElseThrow();
		System.out.println("Current User" + user);
		UserResponseDto userResponseDto= mapper.map(user, UserResponseDto.class);
		return userResponseDto;
	}

	@Override
	public UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//				Optional<User> user= userRepository.findByUsername(username);
//				
//				if(user==null) {
//					System.out.println("user not found");
//					throw new UsernameNotFoundException("User not found");
//				}
//				return user;
				
				return userRepository
						.findByUsername(username)
						.orElseThrow(()->new UsernameNotFoundException("User not found"));
			}
		};

	}

//	@Override
//	public List<UserResponseDto> getAllUser() {
//		List<User> users= userRepository.findAll();
////		List<UserResponseDto> userdtos= users.stream().map(user->{
////			UserResponseDto userResponseDto= mapper.map(user, UserResponseDto.class);
////			return useeResponseDto;
////		}).collect(Collectors.toList());
//		List<UserResponseDto> dtos= users.stream().map(user->{
//			
//		})
//		
//		return userdtos;
//	}

}
