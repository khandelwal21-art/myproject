package com.example.transportsystem.transportsystem.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Userdto {
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	 private Integer id;
	
	 @Column(name = "user_name", nullable=false)
	 private String user_name;
	 
	 private String password;
	 
	 @Column(unique = true, nullable = false)
	    private String email;
	 
	 @Column(length=10)
	 private String phonenumber;
	 
	 private String role;
	 
	 
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Userdto(Integer id, String user_name, String password, String email, String phonenumber, String role) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.email = email;
		this.phonenumber = phonenumber;
		this.role = role;
	}

	@Override
	public String toString() {
		return "Userdto [id=" + id + ", user_name=" + user_name + ", password=" + password + ", email=" + email
				+ ", phonenumber=" + phonenumber + ", role=" + role + "]";
	}

	
	 
}
