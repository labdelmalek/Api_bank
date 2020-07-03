package com.helfarre.BankApi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class corsConfig implements WebMvcConfigurer {
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins( "https://bank-client-app.herokuapp.com","http://localhost:4200","https://bankerapp.herokuapp.com","https://admin-app-bank.herokuapp.com")
        .allowedHeaders("*").exposedHeaders("Authorization,RefreshToken").allowedMethods("*");
        
    }
    @Override 
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) { 
        configurer.favorPathExtension(false); 
    }
    
}
