package com.example.transportsystem.transportsystem.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.transportsystem.transportsystem.dto.Userdto;
import com.example.transportsystem.transportsystem.dto.Userresponse;
import com.example.transportsystem.transportsystem.service.CustomUserService;
import com.example.transportsystem.transportsystem.service.Jwtservice;
import com.example.transportsystem.transportsystem.service.Userservice;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Usercontroller {
 private final Userservice userservice;
 private final AuthenticationManager authenticationManager;
 private final CustomUserService userDetailsService;
 private final Jwtservice Jwtservice;
 
 public Usercontroller(Userservice userservice,AuthenticationManager authenticationManager,CustomUserService userDetailsService,Jwtservice Jwtservice) {
	 this.userservice=userservice;
	 this.authenticationManager=authenticationManager;
	 this.userDetailsService=userDetailsService;
	 this.Jwtservice=Jwtservice;
 }
	 @PostMapping("/register")
	    public ResponseEntity<?> registerUser(@RequestBody Userdto user) {
			Map<String ,String> ValidationErrors=new HashMap<>();
			  if (user.getUser_name() == null || user.getUser_name().isEmpty()) {
				  ValidationErrors.put("user_name", "User name cant be empty");
				  }
			  if (user.getPassword() == null || user.getPassword().isEmpty()) {
				  ValidationErrors.put("Password","Password cannot be null or empty"); }
			  if (user.getEmail() == null || user.getEmail().isEmpty()) {
				  ValidationErrors.put("Email","Email cannot be null or empty"); }
			  if (user.getPhonenumber() == null || user.getPhonenumber().isEmpty()) {
				  ValidationErrors.put("Phonenumber","Phonenumber cannot be null or empty"); }
			  
			  if(!ValidationErrors.isEmpty()) {
				  return new ResponseEntity<>(ValidationErrors, HttpStatus.BAD_REQUEST);
			  }
			
	         return ResponseEntity.ok(userservice.saveuser(user));
	    	
 }
	 

	 @PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody Userdto  request) {
	        try {
	            // Authenticate credentials
	            Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            request.getEmail(),
	                            request.getPassword()
	                    )
	            );

	            // Load UserDetails
	           
					UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	               // Generate JWT
	                String token = Jwtservice.generateToken(request);
	                

	            // Return token
	            return ResponseEntity.ok().body(	token);

	        } catch (BadCredentialsException e) {
	            return ResponseEntity.status(401).body("Invalid credentials");
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Authentication failed");
	        }
	    }
	
}
