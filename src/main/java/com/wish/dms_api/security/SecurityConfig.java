package com.wish.dms_api.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.wish.dms_api.service.IUserService;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired IUserService userService;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver exceptionResolver;
	@Bean
	public JwtFilter jwtFilter() {
		return new JwtFilter(exceptionResolver);
	}
	
	private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**",
            "/webjars/swagger-ui/**",
            "/swagger-ui.html",
            "/api/auth/**",
            "/api/test/**",
            "/documents/**",
            };
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		
		httpSecurity
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(request->request
				.requestMatchers(WHITE_LIST_URL).permitAll()
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.formLogin(Customizer.withDefaults())
		.cors(Customizer.withDefaults())
		.csrf(AbstractHttpConfigurer::disable)
		//single call session, each session will contain an IOC container at each call and delete after working after giving response
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
        ;
		return httpSecurity.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bcryptpasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(bcryptpasswordEncoder());
    	provider.setUserDetailsService(userService.userDetailsService());
    	
    	return provider;
    }
	
	//add this bean to check configuration of token
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
}

