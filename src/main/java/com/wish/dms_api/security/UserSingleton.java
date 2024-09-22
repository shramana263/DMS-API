//package com.wish.dms_api.security;
//
//
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.wish.dms_api.entity.User;
//
//
////import com.wish.dbauth.entity.Users;
//
//public class UserSingleton implements UserDetails{
//
//	private User user;
//	public UserSingleton(User user) {
//        this.user = user;
//    }
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return List.of();
//	}
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return user.getPassword();
//	}
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return user.getUsername();
//	}
////	public Long getId() {
////		// TODO Auto-generated method stub
////		return user.getId();
////	}
//	public Long getId() {
//		// TODO Auto-generated method stub
//		return user.getId();
//	}
//	
//}
//
