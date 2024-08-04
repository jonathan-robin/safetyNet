package com.oc.safetynet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oc.safetynet.models.Person;

@Service
public class PersonsService {

	public Person findPerson(String lastName, String firstName, List<Person> personsList) {
		
		Person user = personsList.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
	      .findFirst().orElse(null);
		return user;
	}
	
	public Person updatePerson(Person person, String address, String city, String phone, Integer zip, String email) {
		
		  person.setAddress(address); 
		  person.setCity(city); 
		  person.setPhone(phone); 
		  person.setZip(zip);
		  person.setEmail(email);
		  
		  return person;
	}
}
