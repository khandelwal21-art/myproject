package com.example.transportsystem.transportsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="transportdestination")
public class TransportDestination {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;

	    @NotBlank(message = "Source cannot be blank") 
	    @Column(nullable = false, name = "source")
	    private String source;
	    
	    @NotBlank(message = "Destination cannot be blank") 
	    @Column(nullable = false)
	    private String destination;	

	    
	    //Getters and setters for source field
	    public String getSource() {
	        return source;
	    }
	    public void setSource(String source) {
	        this.source = source;
	    }
	    
	    //Getters and setters for destination field
	    public String getDestination() {
	        return destination;
	    }
	    public void setDestination(String destination) {
	        this.destination = destination;
	    }

	    //Default constructor 
	    public TransportDestination() {}

	    //Parameterized constructor
	    public TransportDestination(Integer id, String source, String destination) {
	        super();
	        this.id = id;
	        this.source = source;
	        this.destination = destination;
	    }
	    //To string method
		@Override
		public String toString() {
			return "TransportDestination [id=" + id + ", source=" + source + ", destination=" + destination + "]";
		}
	    
	}


