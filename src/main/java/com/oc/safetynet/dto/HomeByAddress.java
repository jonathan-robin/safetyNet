package com.oc.safetynet.dto;

public class HomeByAddress {
	
	private String name; 
	private String firstName; 
	private Integer age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "HomeByAddress [name=" + name + ", firstName=" + firstName + ", age=" + age + "]";
	}
	
}
