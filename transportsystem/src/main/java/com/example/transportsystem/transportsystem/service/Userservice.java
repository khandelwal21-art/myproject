package com.example.transportsystem.transportsystem.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.transportsystem.transportsystem.dto.Userdto;
import com.example.transportsystem.transportsystem.entity.User;
import com.example.transportsystem.transportsystem.repository.Userrepository;

@Service
public class Userservice {

	private final Userrepository userrepository;
	private final PasswordEncoder passwordencoder;
	
	public Userservice(Userrepository userrepository,PasswordEncoder passwordencoder) {
		this.passwordencoder=passwordencoder;
		this.userrepository=userrepository;
	}
	public User saveuser(Userdto user) {
		User registeruser=new User();
		registeruser.setEmail(user.getEmail());
		registeruser.setPassword(passwordencoder.encode(user.getPassword()));
		registeruser.setUser_name(user.getUser_name());
		registeruser.setPhonenumber(user.getPhonenumber());
		registeruser.setRole("user");
		return userrepository.save(registeruser);
		
	}

}
