package com.example.demo.service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enity.user;
import com.example.demo.service.userservice;

@RestController

public class usercontroller {
	@Autowired
    userservice service;
	
	@PostMapping("/createusers")
	public ResponseEntity<String> cretaeuser(@RequestBody user user) {
		
		try{
			service.createusers(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating user: " + e.getMessage());
		}
	}
	 
	@GetMapping("/getallusers")
	public List<user> getall(){
	return 	service.getallusers();	
	
		}
	@GetMapping("/getallbyid/{id}")
	public ResponseEntity<user> getallbyid(@PathVariable Integer id){
		Optional<user> user = service.getusersbyid(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
	}  
	@PutMapping("/update/{id}")
	public Optional<user> update(@PathVariable Integer id,@RequestBody user userdetails) {
		 return service.update(id, userdetails);
				 
		 
	}
	@DeleteMapping("/delete/{id}")
	public Optional<user> delete(@PathVariable Integer id){
		return service.deleteUser(id); 
	}
}
