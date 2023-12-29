package com.oc.safetynet.models;

import java.util.Optional;

public class Person {
	
	private String email;
	private String lastName; 
	private String firstName;
	private String address;
	private String city; 
	private Integer zip;
	private String phone;
	
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	} 
	public String toString() {
		return "name: "+this.getLastName() + "; firstname: " + this.getFirstName();
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	
}	
