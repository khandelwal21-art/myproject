package com.example.transportsystem.transportsystem.service;

import com.example.transportsystem.transportsystem.entity.OtpEntity;
import com.example.transportsystem.transportsystem.repository.OtpRepository;
import com.example.transportsystem.transportsystem.repository.Userrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class ForgotPasswordService {
    
    @Autowired
    private OtpRepository otpRepository;
    
    @Autowired
    private Userrepository userRepository;
    
    @Autowired
    private EnhancedEmailService emailService;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // Step 1: Generate OTP and send to email
    public boolean sendOtpToEmail(String email) {
        try {
            // Check if user exists
            if (userRepository.findByEmail(email)==null) {
                throw new RuntimeException("User with this email does not exist");
            }
            
            // Delete any existing OTP for this email
            otpRepository.deleteByEmail(email);
            
            // Generate 6-digit OTP
            String otp = generateOTP();
            
            // Save OTP to database
            OtpEntity otpEntity = new OtpEntity(email, otp);
            otpRepository.save(otpEntity);
            
            // Send OTP via email
            emailService.sendOtpEmail(email, otp);
            
            System.out.println("OTP sent successfully to: " + email);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error sending OTP: " + e.getMessage());
            throw new RuntimeException("Failed to send OTP: " + e.getMessage());
        }
    }
    
    // Step 2: Verify OTP code
    public boolean verifyOtpCode(String email, String otp) {
        try {
            Optional<OtpEntity> otpEntityOpt = otpRepository.findByEmailAndOtp(email, otp);
            
            if (otpEntityOpt.isPresent()) {
                OtpEntity otpEntity = otpEntityOpt.get();
                
                // Check if OTP is expired
                if (otpEntity.isExpired()) {
                    otpRepository.delete(otpEntity);
                    return false;
                }
                
                // Check if already used
                if (otpEntity.isUsed()) {
                    return false;
                }
                
                // Mark as verified
                otpEntity.setVerified(true);
                otpRepository.save(otpEntity);
                
                System.out.println("OTP verified successfully for: " + email);
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("Error verifying OTP: " + e.getMessage());
            return false;
        }
    }
    
    // Step 3: Change password in database
    public boolean changePassword(String email, String otp, String newPassword) {
        try {
            // Find verified OTP
            Optional<OtpEntity> otpEntityOpt = otpRepository.findByEmailAndVerifiedTrueAndUsedFalse(email);
            
            if (otpEntityOpt.isPresent()) {
                OtpEntity otpEntity = otpEntityOpt.get();
                
                // Verify OTP matches and not expired
                if (otpEntity.getOtp().equals(otp) && !otpEntity.isExpired()) {
                    
                    // Encrypt the new password
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    
                    // Update password in database
                    userRepository.updatePasswordByEmail(email, encryptedPassword);
                    
                    // Mark OTP as used
                    otpEntity.setUsed(true);
                    otpRepository.save(otpEntity);
                    
                    System.out.println("Password changed successfully for: " + email);
                    return true;
                }
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("Error changing password: " + e.getMessage());
            throw new RuntimeException("Failed to change password: " + e.getMessage());
        }
    }
    
    // Generate 6-digit OTP
    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}