package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.enity.user;
import com.example.demo.service.dao.userrepo;

@Service
public class userservice {
	
  @Autowired
	userrepo Repository;
  
  public user createusers(user user) {
	  return Repository.save(user);
  }
  
  
  public  List<user> getallusers(){
	return  Repository.findAll();
	
 }
  
  public Optional<user> getusersbyid(Integer id){
		return  Repository.findById(id);
		
 }
  
 public Optional<user> update(Integer id, user userDetails)
 {
	        return Repository.findById(id).map(user -> {
	            user.setName(userDetails.getName());
	            user.setCity(userDetails.getCity());
	            return Repository.save(user);
	        });
}
 

 public Optional<user> deleteUser(Integer id) {
     Optional<user> user = Repository.findById(id);
     if (user.isPresent()) {
         Repository.deleteById(id);
     }
     return user; 
 }
	 
	 
 }
 
		


