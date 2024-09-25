package com.wish.dms_api.service.implement;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wish.dms_api.dto.UserResponseDto;
import com.wish.dms_api.entity.User;
import com.wish.dms_api.repository.IUserRepository;
import com.wish.dms_api.security.JwtService;
import com.wish.dms_api.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class UserService implements IUserService{

	@Autowired IUserRepository userRepository;
	@Autowired ModelMapper mapper;
	@Autowired JwtService jwtService;
	
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
		System.out.println("hello currentuser");
//		UserSingleton currentUserDetails = (UserSingleton)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			System.out.println(SecurityContextHolder.getContext());
			User currentUserDetails= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			System.out.println(currentUserDetails);
			User user= userRepository.findById(currentUserDetails.getId()).orElseThrow();
			System.out.println("Current User" + user);
			UserResponseDto userResponseDto= mapper.map(user, UserResponseDto.class);
			return userResponseDto;
		}
		catch(Exception ex) {
			System.out.println(ex);
			return null;
		}
		
		
		// Another way to get current user
		
//		HttpServletRequest request = ((ServletRequestAttributes) Objects
//                .requireNonNull(RequestContextHolder.getRequestAttributes()))
//                .getRequest();
//        String token = request.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Remove "Bearer " from the token
//        }
//
//        String username = jwtService.extractUsername(token);
//        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return mapper.map(user, UserResponseDto.class);
		
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
