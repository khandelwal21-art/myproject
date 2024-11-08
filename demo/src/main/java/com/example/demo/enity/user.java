package com.example.demo.enity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class user {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int userId;

	
    private String name;
    private String city;
    
    public user() {
    	
    }
    
    public user(int userId, String name, String city) {
		super();
		this.userId = userId;
		this.name = name;
		this.city = city;
	}
    
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "user [userId=" + userId + ", name=" + name + ", city=" + city + "]";
	}}
