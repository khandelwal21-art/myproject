package com.example.transportsystem.transportsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.transportsystem.transportsystem.entity.TransportDestination;

@Repository
public interface Transportdestinationrepository extends JpaRepository<TransportDestination,Integer> {

}
