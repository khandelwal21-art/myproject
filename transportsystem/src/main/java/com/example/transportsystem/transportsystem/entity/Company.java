package com.example.transportsystem.transportsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="company")

public class Company {
    // Fields
	 @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	 
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

    // Default constructor
    public Company() {
        
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Parameterized constructor
    public Company(String category, String companyName, String phoneNumber) {
        this.category = category;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter and Setter for companyName
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // toString method
    @Override
    public String toString() {
        return "Company{" +
                "category='" + category + '\'' +
                ", companyName='" + companyName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
