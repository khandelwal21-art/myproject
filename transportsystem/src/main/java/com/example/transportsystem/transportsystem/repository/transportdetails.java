package com.example.transportsystem.transportsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.transportsystem.transportsystem.entity.TransportDetails;
@Repository
public interface transportdetails extends JpaRepository<TransportDetails,Integer>{
	
    List<TransportDetails> findByCompany_PhoneNumber(String phoneNumber);
}
