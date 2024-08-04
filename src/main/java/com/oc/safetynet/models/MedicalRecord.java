package com.oc.safetynet.models;

import java.util.List;

public class MedicalRecord {
	
	private String firstName; 
	private String lastName; 
	private List<String> medications; 
	private String birthdate; 
	private List<String> allergies;
	
	public MedicalRecord(String firstName, String lastName, List<String>allergies, String birthdate, List<String>medications) {
		this.firstName = firstName; 
		this.lastName =lastName; 
		this.allergies = allergies; 
		this.birthdate = birthdate; 
		this.medications = medications;
	}
	
	
	public MedicalRecord() { 
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	} 
	


}
