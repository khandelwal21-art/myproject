package com.example.transportsystem.transportsystem.dto;

public class Userresponse {
private String jwttoken;
private String username;


public String getJwttoken() {
	return jwttoken;
}
public void setJwttoken(String jwttoken) {
	this.jwttoken = jwttoken;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}

public Userresponse() {
	
}
public Userresponse(String jwttoken, String username) {
	super();
	this.jwttoken = jwttoken;
	this.username = username;
}
@Override
public String toString() {
	return "Userresponse [jwttoken=" + jwttoken + ", username=" + username + "]";
}

}
