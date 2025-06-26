package com.example.transportsystem.transportsystem.controller;

import com.example.transportsystem.transportsystem.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/forgot-password")
@CrossOrigin(origins = "http://localhost:3000")
public class ForgotPasswordController {
    
    @Autowired
    private ForgotPasswordService otpService;
    
    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(request);
        try {
            String email = request.get("email");
            
            if (email == null || email.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Validate email format
            if (!isValidEmail(email)) {
                response.put("success", false);
                response.put("message", "Please enter a valid email address");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean otpSent = otpService.sendOtpToEmail(email);
            
            if (otpSent) {
                response.put("success", true);
                response.put("message", "OTP sent to your email successfully");
                response.put("email", email);
                response.put("expiryMinutes", 5);
            } else {
                response.put("success", false);
                response.put("message", "Failed to send OTP. Please try again.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = request.get("email");
            String otp = request.get("otp");
            
            if (email == null || otp == null || email.trim().isEmpty() || otp.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Email and OTP are required");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (otp.length() != 6) {
                response.put("success", false);
                response.put("message", "OTP must be 6 digits");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean isValid = otpService.verifyOtpCode(email, otp);
            
            if (isValid) {
                response.put("success", true);
                response.put("message", "OTP verified successfully");
                response.put("email", email);
            } else {
                response.put("success", false);
                response.put("message", "Invalid or expired OTP");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error verifying OTP: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = request.get("email");
            String otp = request.get("otp");
            String newPassword = request.get("newPassword");
            
            if (email == null || otp == null || newPassword == null || 
                email.trim().isEmpty() || otp.trim().isEmpty() || newPassword.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Email, OTP, and new password are required");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (newPassword.length() < 6) {
                response.put("success", false);
                response.put("message", "Password must be at least 6 characters long");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = otpService.changePassword(email, otp, newPassword);
            
            if (success) {
                response.put("success", true);
                response.put("message", "Password reset successfully");
                response.put("email", email);
            } else {
                response.put("success", false);
                response.put("message", "Failed to reset password. Invalid or expired OTP.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error resetting password: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, Object>> resendOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = request.get("email");
            
            if (email == null || email.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean otpSent = otpService.sendOtpToEmail(email);
            
            if (otpSent) {
                response.put("success", true);
                response.put("message", "New OTP sent to your email successfully");
                response.put("email", email);
                response.put("expiryMinutes", 5);
            } else {
                response.put("success", false);
                response.put("message", "Failed to resend OTP. Please try again.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}