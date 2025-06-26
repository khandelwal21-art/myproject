package com.example.transportsystem.transportsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EnhancedEmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset OTP - Transport System");
            message.setText(buildOtpEmailContent(otp));
            
            mailSender.send(message);
            System.out.println("OTP email sent successfully to: " + toEmail);
            
        } catch (Exception e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
    
    private String buildOtpEmailContent(String otp) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        return "Dear User,\n\n" +
                "You have requested to reset your password for Transport System.\n\n" +
                "Your One-Time Password (OTP) is: " + otp + "\n\n" +
                "IMPORTANT INFORMATION:\n" +
                "• Generated at: " + now.format(formatter) + "\n" +
                "• Valid for: 10 minutes\n" +
                "• Use this OTP only once\n" +
                "• Do not share this OTP with anyone\n\n" +
                "If you did not request this password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Transport System Team";
    }
}