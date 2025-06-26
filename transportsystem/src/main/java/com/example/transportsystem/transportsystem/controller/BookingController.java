	package com.example.transportsystem.transportsystem.controller;
	
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RestController;
	
	import com.example.transportsystem.transportsystem.dto.Booking;
	import com.example.transportsystem.transportsystem.service.Bookingservice;
import com.example.transportsystem.transportsystem.service.Sendmail;

import jakarta.validation.Valid;
	@RestController
	@CrossOrigin(origins = "http://localhost:3000")
	public class BookingController {
	
		private final Bookingservice bookingservice;
		private final Sendmail sendingmail;
	
		public BookingController(Bookingservice bookingservice,Sendmail sendingmail ) {
			this.bookingservice=bookingservice;
			this.sendingmail=sendingmail;
		
		}
		 @PostMapping("/submitAll")
		    public ResponseEntity<?> submitAllData(@Valid @RequestBody Booking allData) {
			 System.out.println(allData);
			 
			 
			 Map<String, String> errors = new HashMap<>();
			 // Trimming companyName to remove leading/trailing whitespace
		        if (allData.getCompanyName() != null) {
		            allData.setCompanyName(allData.getCompanyName().trim());
		        }
	
		        // Validate source and destination
		        if (allData.getSource() == null || allData.getSource().trim().isEmpty()) {
		            errors.put("source", "Source location is required");
		        }
		        
		        if (allData.getDestination() == null || allData.getDestination().trim().isEmpty()) {
		            errors.put("destination", "Destination location is required");
		        }
		        
		        // Validate transport type
		        if (allData.getTransportType() == null || allData.getTransportType().trim().isEmpty()) {
		            errors.put("transportType", "Transport type is required");
		        }
		        
		        // Validate weight if transport type is Truck or Container
		        if ("TRUCK".equalsIgnoreCase(allData.getTransportType()) || "CONTAINER".equalsIgnoreCase(allData.getTransportType())) {
		            if (allData.getWeight() == null) {
		                errors.put("weight", "Weight is required for Truck or Container");
		            }
		            
		            if (allData.getLength() == null || allData.getLength().trim().isEmpty()) {
		                errors.put("length", "Length is required for Truck or Container");
		            }
		            
		            if (allData.getHeight() == null || allData.getHeight().trim().isEmpty()) {
		                errors.put("height", "Height is required for Truck or Container");
		            }
		        }
		        
		        // Validate small truck details if transport type is Small cart
		        if ("SMALLCART".equalsIgnoreCase(allData.getTransportType())) {
		            if (allData.getSmall_truck_details() == null || allData.getSmall_truck_details().trim().isEmpty()) {
		                errors.put("small_truck_details", "Small truck details are required for Smallcart");
		            }
		        }
		        
		        // Validate company details
		        if (allData.getCategory() == null || allData.getCategory().trim().isEmpty()) {
		            errors.put("category", "Business category is required");
		        }
		        
		        if (allData.getCompanyName() == null || allData.getCompanyName().trim().isEmpty()) {
		            errors.put("companyName", "Company name is required");
		        }
		        
		        if (allData.getPhoneNumber() == null || allData.getPhoneNumber().trim().isEmpty()) {
		            errors.put("phoneNumber", "Phone number is required");
		        } else if (!allData.getPhoneNumber().matches("^[0-9]{10}$")) {
		            errors.put("phoneNumber", "Phone number must be exactly 10 digits");
		        }
		        if(!errors.isEmpty()) {
					return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
				}
		        
		        
		        sendingmail.sendemailtouser(allData);
		        
		        return ResponseEntity.ok(bookingservice.saveDetails(allData));
		    }
		 
		 @GetMapping("/booking/{phoneNumber}")
		 public ResponseEntity<List<Booking>> getByPhone(@PathVariable String phoneNumber) {
			 return ResponseEntity.ok(bookingservice.getTransportdetails(phoneNumber));
		 }
		 
		 
		 
		 
		 
		 
		 
		 
	}	 
		 
		 
		 
		 
	
