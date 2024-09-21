package com.wish.dms_api.security;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.wish.dms_api.service.IUserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired JwtService jwtService;
	@Autowired ApplicationContext context;
	private HandlerExceptionResolver exceptionResolver;
	@Autowired
	public JwtFilter(HandlerExceptionResolver exceptionResolver) {
		this.exceptionResolver=exceptionResolver;
	}
	
	@Autowired IUserService userService;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader= request.getHeader("Authorization");
		String token= null;
		String username=null;
		try {
			
			if(authHeader!=null && authHeader.contains("Bearer ") ) {
				
				System.out.println("inside authheader");
				token = authHeader.substring(7);
				System.out.println(token);
				username=jwtService.extractUsername(token);
				System.out.println(username);
				
			}
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
//				UserDetails userDetails = context.getBean(AuthUserDetailsService.class).loadUserByUsername(username);
				
				 UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
				
				System.out.println("before validate");
				if(jwtService.validateToken(token,userDetails)) {
					
					UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response);
		}catch(Exception ex) {
			exceptionResolver.resolveException(request, response, null, ex);
		}
		
		
	}

	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    String requestURI = request.getRequestURI();
	    if (requestURI.contains("/api/auth/"))
	    {
	        return true; // Do not filter requests to API endpoints
	    }
//	    String method = request.getMethod();
//	    if (method.equals("OPTIONS")) {
//	        return true; // Do not filter OPTIONS requests (used for preflight requests)
//	    }
	    return false; // Filter other requests
	}	
}

