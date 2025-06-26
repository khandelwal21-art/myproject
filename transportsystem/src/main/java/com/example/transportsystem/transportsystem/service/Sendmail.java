package com.example.transportsystem.transportsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.transportsystem.transportsystem.dto.Booking;

import jakarta.validation.Valid;

@Service
public class Sendmail {
	   @Autowired
	    private JavaMailSender mailSender;
	
	public void sendemailtouser(@Valid Booking allData) {
		String userEmail=getCurrentUserEmail();
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(userEmail);
	        message.setSubject("Booking Confirmation");
	        message.setText("Booking Details:\n" +
	                "Company Name: " + allData.getCompanyName() + "\n" +
	                "Phone Number: " + allData.getPhoneNumber() + "\n" +
	                "Category: " + allData.getCategory() + "\n" +
	                "Source: " + allData.getSource() + "\n" +
	                "Destination: " + allData.getDestination() + "\n" + // Make sure the field name matches your Booking class!
	                "Transport Type: " + allData.getTransportType() + "\n" +
	                "Weight: " + allData.getWeight() + "\n" +
	                "Length: " + allData.getLength() + "\n" +
	                "Height: " + allData.getHeight() + "\n" +
	                "Small Truck Details: " + (allData.getSmall_truck_details() != null ? allData.getSmall_truck_details() :
	                	"N/A"));
	        mailSender.send(message);
		
	}
	
	private String getCurrentUserEmail() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        return userDetails.getUsername(); // In your case, this is the email
	    }
	    throw new RuntimeException("User not authenticated");
	}
}
