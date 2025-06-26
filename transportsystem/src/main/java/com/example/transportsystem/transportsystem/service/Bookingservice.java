package com.example.transportsystem.transportsystem.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.transportsystem.transportsystem.dto.Booking;
import com.example.transportsystem.transportsystem.entity.Company;
import com.example.transportsystem.transportsystem.entity.TransportDestination;
import com.example.transportsystem.transportsystem.entity.TransportDetails;
import com.example.transportsystem.transportsystem.repository.Companydetailsrepository;
import com.example.transportsystem.transportsystem.repository.Transportdestinationrepository;
import com.example.transportsystem.transportsystem.repository.transportdetails;
import com.example.transportsystem.transportsystem.transporttype.TransportType;

@Service
public class Bookingservice {
	private final Companydetailsrepository companydetailsrepository;
	private final Transportdestinationrepository repository;
	private final  transportdetails Transportdetailsrepo;

	// injecting repository using constructor injection
	public  Bookingservice (Companydetailsrepository companydetailsrepository,Transportdestinationrepository repository,transportdetails Transportdetailsrepo) {
		this.companydetailsrepository=companydetailsrepository;
		this.Transportdetailsrepo=Transportdetailsrepo;
		this.repository=repository;
	}
	
	//method for saving form details in database
	public TransportDetails saveDetails(Booking bookingDetails){
		//saving transport destination details
		TransportDestination destination = new TransportDestination();
        destination.setSource(bookingDetails.getSource());
        destination.setDestination(bookingDetails.getDestination());
        destination = repository.save(destination);
        
	    //saving company details
        Company company=new Company();
        company.setCategory(bookingDetails.getCategory());
        company.setCompanyName(bookingDetails.getCompanyName());
        company.setPhoneNumber(bookingDetails.getPhoneNumber());
        company = companydetailsrepository.save(company);
        
        //saving transport details
	    TransportDetails details = new TransportDetails();
	    details.setWeight(bookingDetails.getWeight());
        details.setLength(bookingDetails.getLength());
        details.setHeight(bookingDetails.getHeight());
        details.setTransportType(TransportType.valueOf(bookingDetails.getTransportType()));
        details.setWeightUnit(bookingDetails.getWeightUnit());
        details.setSmall_truck_details(bookingDetails.getSmall_truck_details());
        details.setCompany(company);
        details.setTransportDestination(destination);
        
        return Transportdetailsrepo.save(details);
       
        
	}

	public List<Booking> getTransportdetails(String phonenumber ) {
		List<TransportDetails> details =Transportdetailsrepo.findByCompany_PhoneNumber(phonenumber);
		System.out.println(details);
		return null;
	}

}
