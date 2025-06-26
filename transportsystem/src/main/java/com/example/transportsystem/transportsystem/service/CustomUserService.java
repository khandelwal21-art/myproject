package com.example.transportsystem.transportsystem.service;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.transportsystem.transportsystem.entity.User;
import com.example.transportsystem.transportsystem.repository.Userrepository;

@Service
public class CustomUserService implements UserDetailsService {

	private final  Userrepository userrepo;
	public CustomUserService(Userrepository userrepo){
		this.userrepo=userrepo;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userrepo.findByEmail(username);
		if(Objects.isNull(user)) {
	    	  System.out.println("user not found");
	    	  throw new UsernameNotFoundException("user not found");
	      }
	      
			return new CustomDetails(user);
		
	}

}
