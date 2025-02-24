package com.wish.dms_api.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${document.path}")
    private String DOCUMENT_PATH;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // or any other front-end URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {

         registry.addResourceHandler("/documents/**")
                 .addResourceLocations("file:D:/springboot/dms-api/src/main/resources/static/documents/");


     }
     
//     @Bean
//     public CorsFilter corsFilter() {
//         CorsConfiguration corsConfig = new CorsConfiguration();
//         corsConfig.setAllowCredentials(true);
//         corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
//  // Replace with your frontend's origin
//         corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
//         corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//         corsConfig.setExposedHeaders(List.of("X-Custom-Header"));
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", corsConfig);
//
//         return new CorsFilter(source);
//     }

}
