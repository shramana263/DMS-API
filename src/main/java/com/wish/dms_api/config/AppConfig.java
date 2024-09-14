package com.wish.dms_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wish.dms_api.security.CorsFilter;
@Configuration
public class AppConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(); 

    }
}
