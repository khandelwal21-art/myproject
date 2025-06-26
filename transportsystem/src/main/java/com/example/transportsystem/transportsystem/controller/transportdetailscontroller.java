package com.example.transportsystem.transportsystem.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.transportsystem.transportsystem.entity.TransportDetails;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class transportdetailscontroller {
	@PostMapping("/get-truck-length-options")
 private List<String> getlength(@RequestBody TransportDetails transportdetails) {
	 List<String> trucklength =new ArrayList<>();
	 if("Ton".equals(transportdetails.getWeightUnit())) {
		 if(transportdetails.getWeight()>=0 && transportdetails.getWeight()<=1) {
			 trucklength=List.of("7ft", "8ft", "9ft", "10ft", "11ft", "12ft", 
					 "13ft", "14ft");
		 }
		 else if(transportdetails.getWeight()>=2&&transportdetails.getWeight()<=4) {
			 trucklength=List.of("14ft", "15ft", "16ft", 
					 "17ft", "18ft","19ft","20ft","21ft","22ft");
		 }else if (transportdetails.getWeight()==5){
			 trucklength=List.of(  "17ft", "18ft","19ft","20ft","21ft","22ft");
		 }
		 else if(transportdetails.getWeight() >=6 &&transportdetails.getWeight()<=10){
			 trucklength=List.of(  "19ft","20ft","22ft");

		 }else {
			 trucklength=List.of( "22ft");
		 }
	 }
	 else if("Kg".equals(transportdetails.getWeightUnit()))
	 {
		 if(transportdetails.getWeight()>=0 && transportdetails.getWeight()<=1) {
			 trucklength=List.of("7ft", "8ft", "9ft", "10ft", "11ft", "12ft", 
					 "13ft", "14ft");
		 }
		 else if(transportdetails.getWeight()>=2&&transportdetails.getWeight()<=4) {
			 trucklength=List.of("14ft", "15ft", "16ft", 
					 "17ft", "18ft","19ft","20ft","21ft","22ft");
		 }else if (transportdetails.getWeight()==5){
			 trucklength=List.of(  "17ft", "18ft","19ft","20ft","21ft","22ft");
		 }
		 else if(transportdetails.getWeight() >=6 &&transportdetails.getWeight()<=10){
			 trucklength=List.of(  "19ft","20ft","22ft");

		 }else {
			 trucklength=List.of( "22ft");
		 }
	 }
        return trucklength;
 }

}
