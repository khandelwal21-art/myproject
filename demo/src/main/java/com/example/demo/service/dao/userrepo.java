package com.example.demo.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enity.user;

@Repository
public interface userrepo  extends JpaRepository<user, Integer>  {

}
