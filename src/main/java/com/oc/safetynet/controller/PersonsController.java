package com.oc.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.safetynet.models.Database;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;
import com.oc.safetynet.service.SafetyNetService;

import dto.DtoPersonWithMedication;
import dto.PersonByFirestation;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("person")
public class PersonsController {

	Logger logger = LoggerFactory.getLogger(PersonsController.class);
	
	@Autowired
	private DatabaseService databaseService;
	
	@Autowired
	private SafetyNetService snSvc;

  @GetMapping("/persons")
  Stream<Person> all() {  
	  logger.info("/firestation called!");
	  Stream<Person> persons = databaseService.getDatabase().getPersons();
	  logger.info("persons List: {}", persons);
	  return persons;
  }
  
  @PostMapping("")
  ResponseEntity<String> addPerson(@RequestParam String email, @RequestParam String lastName, @RequestParam String firstName,
		  @RequestParam String address, @RequestParam String city, @RequestParam String phone, @RequestParam Integer zip) {
	  
	  Person newPerson = new Person(); 
	  
	  newPerson.setAddress(address);
	  newPerson.setCity(city); 
	  newPerson.setEmail(email);
	  newPerson.setFirstName(firstName);
	  newPerson.setLastName(lastName);
	  newPerson.setZip(zip);
	  newPerson.setPhone(phone);
	  
	  List<Person> personsTmp = databaseService.getDatabase().getPersons().toList();
	  
	  List<Person> person = new ArrayList<>();
	  
	  Person user = findPerson(newPerson.getLastName(), newPerson.getFirstName(), personsTmp);
	  
	  if (user != null) 
		  return new ResponseEntity<String>("User already exists.", HttpStatus.BAD_REQUEST);

	  person.add(newPerson); 
	  person.addAll(personsTmp); 
	  
	  databaseService.getDatabase().setPersons(person);
	  return new ResponseEntity<String>("User added : " + newPerson.getFullName(), HttpStatus.ACCEPTED);
  }

	private Person findPerson(String lastName, String firstName, List<Person> personsList) {
		
		Person user = personsList.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
	      .findFirst().orElse(null);
		return user;
	}
  
  @PutMapping("")
  ResponseEntity<String> updatePerson(@RequestParam String email, @RequestParam String lastName, @RequestParam String firstName,
		  @RequestParam String address, @RequestParam String city, @RequestParam String phone, @RequestParam Integer zip) {
	  
	  Stream<Person> personsTmp = databaseService.getDatabase().getPersons();
	  Person person = findPerson(lastName, firstName, personsTmp.toList());
	  
	  if(person != null) {
		  person.setAddress(address); 
		  person.setCity(city); 
		  person.setPhone(phone); 
		  person.setZip(zip);
		  person.setEmail(email);
		  
		  List<Person> persons = new ArrayList<>();
		  List<Person> _personsTmp = databaseService.getDatabase().getPersons().toList();
		  
		  persons.add(person); 
		  persons.addAll(_personsTmp); 
		  
		  databaseService.getDatabase().setPersons(persons);
		  
		  return new ResponseEntity<String>("User " + person.getFullName() + " updated", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Can't find user to update with name: " + firstName + " " + lastName, HttpStatus.BAD_REQUEST);
		}	
  }
  
  @DeleteMapping("")
  ResponseEntity<String> deletePerson(@RequestParam String lastName, @RequestParam String firstName) {
	  
	  List<Person> persons = databaseService.getDatabase().getPersons().toList();	  
	  List<Person> personsTmp = new ArrayList<>();
	  
	  Person personToDelete = findPerson(lastName, firstName, persons);
	  
	  if (personToDelete == null)
		  return new ResponseEntity<String>("Can't find user to delete: "+ lastName + " " + firstName, HttpStatus.NOT_FOUND);
	  
	  persons.forEach(p -> {
		  if (p.getLastName().equals(lastName) && p.getFirstName().equals(firstName))
			  return;
		  else 
			  personsTmp.add(p);
	  });
	  
	  databaseService.getDatabase().setPersons(personsTmp);
	  return new ResponseEntity<String>("User: " + lastName + " " + firstName + " deleted", HttpStatus.OK);
  }	
  
  @GetMapping("/personInfo")
  ResponseEntity<List<DtoPersonWithMedication>> getPersonInfo(@RequestParam String lastName, @RequestParam(required=false) String firstName ){
	  
	  List<Person> persons = snSvc.getPersons(lastName, firstName);
	  
	  List<DtoPersonWithMedication> dto = new ArrayList<>();
	  
	  persons.forEach(person -> {
		  MedicalRecord mr = snSvc.getMedicalRecord(person.getLastName(), person.getFirstName());
		  
		  DtoPersonWithMedication dtoTmp = new DtoPersonWithMedication();
		  dtoTmp.setPerson(person); 
		  dtoTmp.setMedicalRecord(mr);
		  
		  dto.add(dtoTmp);
		  
	  });

	  return new ResponseEntity<List<DtoPersonWithMedication>>(dto, HttpStatus.OK);
	  
  }
  
}
