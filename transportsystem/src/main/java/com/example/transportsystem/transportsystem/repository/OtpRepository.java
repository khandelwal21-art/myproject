package com.example.transportsystem.transportsystem.repository;

import com.example.transportsystem.transportsystem.entity.OtpEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    
    Optional<OtpEntity> findByEmailAndOtp(String email, String otp);
    
    Optional<OtpEntity> findByEmailAndVerifiedTrueAndUsedFalse(String email);
    
    @Modifying
    @Transactional
    void deleteByEmail(String email);
}