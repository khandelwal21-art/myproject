package com.example.transportsystem.transportsystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.transportsystem.transportsystem.entity.User;

@Repository
public interface Userrepository extends JpaRepository<User,Integer>{
	User findByEmail(String  email);
	 @Modifying
	    @Transactional
	    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
	    void updatePasswordByEmail(String email, String password);
}
