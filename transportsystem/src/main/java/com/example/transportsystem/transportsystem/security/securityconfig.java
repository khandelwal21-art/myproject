package com.example.transportsystem.transportsystem.security;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.transportsystem.transportsystem.service.CustomUserService;

import jakarta.servlet.http.HttpServletResponse;
@Configuration
@EnableWebSecurity
public class securityconfig {
	
	private CustomUserService service;
    private jwtAuthenticationfilter jwtAuthenticationfilter;
	
	public securityconfig(CustomUserService service,jwtAuthenticationfilter jwtAuthenticationfilter) {
		this.service=service;
		this.jwtAuthenticationfilter=jwtAuthenticationfilter;
		
	}
	@Bean
	 public SecurityFilterChain filterchain(HttpSecurity security) throws Exception {
		 
		
		 security.csrf(AbstractHttpConfigurer::disable);
		 security.cors(Customizer.withDefaults());
		 security.authorizeHttpRequests(authorize->{
			 authorize.requestMatchers("/register","/login","/api/forgot-password/*").permitAll();
			 authorize.anyRequest().authenticated(); 

			 });

		    security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		 
		 security.addFilterBefore(jwtAuthenticationfilter,
				 UsernamePasswordAuthenticationFilter.class);
		 security.exceptionHandling(exception -> exception
			        .authenticationEntryPoint((request, response, authException) -> {
			            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			        })
			    );
		return security.build();
		}
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() 
	        {
	            @Override
	            public void addCorsMappings(CorsRegistry registry)
	            {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:3000")
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                        .allowedHeaders("*")
	                        .allowCredentials(true)
	                        .maxAge(3600);
	            }
	        };
	 };
	
	
	@Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	
	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
		provider.setUserDetailsService(service);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configouration) throws Exception {
		return configouration.getAuthenticationManager();
		
	}
}
