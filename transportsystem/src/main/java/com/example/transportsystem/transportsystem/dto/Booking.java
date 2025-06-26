package com.example.transportsystem.transportsystem.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Booking {
	
	    private Double weight;
	    @Column(nullable=false)
		@NotBlank(message="length can not be empty")
	    private String length;
	    
	    @Column(nullable=false)
		@NotBlank(message="height can not be empty")
	    private String height;
	    @NotBlank(message = "Transport type is required")
	    private String transportType;
	    
	    @Column(nullable=false)
		@NotBlank(message="weight unit can not be zero")
	    private String weightUnit;
	    @Column(nullable=false)
		@NotBlank(message="smallTruckDetails can not be empty")
		private String small_truck_details;
	    
	    @NotBlank(message = "Source cannot be blank") 
	    @Column(nullable = false, name = "source")
	    private String source;
	    
	    @NotBlank(message = "Destination cannot be blank") 
	    @Column(nullable = false)
	    private String destination;
	    
	    @NotBlank(message = "catagory cannot be blank") 
	    @Column(nullable = false, name = "category")
	    private String category;
	    
	    @NotBlank(message = "company Name cannot be blank") 
	    @Column(nullable = false, name = "companyName")
	    private String companyName;
	    
		 @NotBlank(message = "phone number cannot be blank") 
		 @Column(nullable = false, name = "phoneNumber")
		 @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
		 @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	    private String phoneNumber;
	    

	    public Double getWeight() {
			return weight;
		}

		public void setWeight(Double weight) {
			this.weight = weight;
		}

		public String getLength() {
			return length;
		}

		public void setLength(String length) {
			this.length = length;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getTransportType() {
			return transportType;
		}

		public void setTransportType(String transportType) {
			this.transportType = transportType;
		}

		public String getWeightUnit() {
			return weightUnit;
		}

		public void setWeightUnit(String weightUnit) {
			this.weightUnit = weightUnit;
		}
        

		public String getSmall_truck_details() {
			return small_truck_details;
		}

		public void setSmall_truck_details(String small_truck_details) {
			this.small_truck_details = small_truck_details;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public Booking() {}

	   
	   

	    public Booking(Double weight, @NotBlank(message = "length can not be empty") String length,
				@NotBlank(message = "height can not be empty") String height,
				@NotBlank(message = "Transport type is required") String transportType,
				@NotBlank(message = "weight unit can not be zero") String weightUnit,
				@NotBlank(message = "smallTruckDetails can not be empty") String small_truck_details,
				@NotBlank(message = "Source cannot be blank") String source,
				@NotBlank(message = "Destination cannot be blank") String destination,
				@NotBlank(message = "catagory cannot be blank") String category,
				@NotBlank(message = "company Name cannot be blank") String companyName,
				@NotBlank(message = "phone number cannot be blank") @Size(min = 10, max = 10, message = "Phone number must be 10 digits") @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits") String phoneNumber) {
			super();
			this.weight = weight;
			this.length = length;
			this.height = height;
			this.transportType = transportType;
			this.weightUnit = weightUnit;
			this.small_truck_details = small_truck_details;
			this.source = source;
			this.destination = destination;
			this.category = category;
			this.companyName = companyName;
			this.phoneNumber = phoneNumber;
		}

		@Override
		public String toString() {
			return "Booking [weight=" + weight + ", length=" + length + ", height=" + height + ", transportType="
					+ transportType + ", weightUnit=" + weightUnit + ", small_truck_details=" + small_truck_details
					+ ", source=" + source + ", destination=" + destination + ", category=" + category
					+ ", companyName=" + companyName + ", phoneNumber=" + phoneNumber + "]";
		}

	
	}


