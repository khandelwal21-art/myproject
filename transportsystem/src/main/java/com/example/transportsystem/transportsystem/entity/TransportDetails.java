package com.example.transportsystem.transportsystem.entity;

import com.example.transportsystem.transportsystem.transporttype.TransportType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="transportdetails")
public class TransportDetails {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
   private Integer id;
	
	// Truck weight field
	@Column(nullable=false)
   private Double weight;
	
	//Truck length field
	@Column(nullable=false)
	@NotBlank(message="length can not be empty")
   private String length;
	
	//Truck height field
	@Column(nullable=false)
	@NotBlank(message="height can not be empty")
   private String height;
	
	//enum
	private TransportType transportType;
	
	//Truck weight unit field
	@Column(nullable=false)
	@NotBlank(message="weight unit can not be zero")
	private String weightUnit;
	
	@Column(nullable=false)
	@NotBlank(message="smallTruckDetails can not be empty")
			private String small_truck_details;
	
	@ManyToOne
	@JoinColumn(name="transportdestination_id")
	private TransportDestination tansportDestination;
	
	 @ManyToOne
	 @JoinColumn(name = "company_id")
	 private Company company;
	 
	 @ManyToOne // Many TransportDetails can belong to One User
	  @JoinColumn(name = "user_id", nullable = false) // This creates a foreign key column named 'user_id'
	    private User user; // Reference to the User entity
	 
	    public User getUser() {
	        return user;
	    }
	    public void setUser(User user) {
	        this.user = user;
	    }
	//getters and setters for id
      public Integer getId() {
			return id;
	  }
	public void setId(Integer id) {
		this.id = id;
	}
	
	//getters and setters for Truck weight
		public Double getWeight() {
			return weight;
		}
		public void setWeight(Double weight) {
			this.weight = weight;
		}
		
	//getters and setters for Truck length
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	//getters and setters for Truck height
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	//getters and setters for weight unit
	   public String getWeightUnit() {
			return weightUnit;
		}
		public void setWeightUnit(String weightUnit) {
			this.weightUnit = weightUnit;
		}
		//getter for small_truck_details
		public String getSmall_truck_details() {
		    return small_truck_details;
		}
		//setter for small_truck_details
		public void setSmall_truck_details(String small_truck_details) {
		    this.small_truck_details = small_truck_details;
		}
	
	//getters and setters for enum constant transport type
	public TransportType getTransportType() {
		return transportType;
	}
	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	
	//getter for Transport Destination
	public TransportDestination getTransportDestination() {
		return tansportDestination;
	}

	//setter for Transport Destination
	public void setTransportDestination(TransportDestination tansportDestination) {
		this.tansportDestination = tansportDestination;
	}
	

	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	//Default constructor 
	public TransportDetails() {
	} 
	
	//parameteried constructor
	public TransportDetails(Double weight,  String length,
			String height, TransportType transportType,
			 String weightUnit,
			 String small_truck_details,
			TransportDestination tansportDestination,
			Company company,
			User user) {
		super();
		this.weight = weight;
		this.length = length;
		this.height = height;
		this.transportType = transportType;
		this.weightUnit = weightUnit;
		this.small_truck_details = small_truck_details;
		this.tansportDestination = tansportDestination;
		this.company = company;
		this.user = user; // Set the user
	}
	
	//to string method
	
	@Override
	public String toString() {
		return "TransportDetails [id=" + id + ", weight=" + weight + ", length=" + length + ", height=" + height
				+ ", transportType=" + transportType + ", weightUnit=" + weightUnit + ", small_truck_details="
				+ small_truck_details + ", tansportDestination=" + tansportDestination + ", company=" + company
				+ ", user=" + user + "]";
	}
	
	
	
	
}
