package com.wish.dms_api.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wish.dms_api.entity.User;
import com.wish.dms_api.repository.IUserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService{

//	@Autowired UserPrincipal userPrincipal;
	@Autowired
	IUserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user= userRepository.findByUsername(username);
		
		if(user==null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}
//		return null;
		return new UserSingleton(user);
	}

}
