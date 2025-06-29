package com.example.transportsystem.transportsystem.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.transportsystem.transportsystem.entity.User;



public class CustomDetails implements UserDetails{

	private User user;
	
	   public CustomDetails(User user) {
		   this.user=user;
		   
	   }
	   
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
			return true;
		}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
			return true;
		}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
