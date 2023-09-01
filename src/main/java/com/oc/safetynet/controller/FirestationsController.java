package com.oc.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.safetynet.models.Firestation;
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;

@RestController
@RequestMapping("firestation")
public class FirestationsController {

	@Autowired
	private DatabaseService databaseService;
	
	  @GetMapping("/firestations")
	  Stream<Firestation> all() {
			  return databaseService.getDatabase().getFirestations();
	  }
	  
	  @PostMapping("")
	  List<Firestation> addFirestation(@RequestParam String address, @RequestParam String station) {
		  
		  Firestation newFs = new Firestation(address, station); 
		  
		  
		  List<Firestation> fsTmp = databaseService.getDatabase().getFirestations().toList();
		  
		  List<Firestation> fs = new ArrayList<>();
		  
		  fs.add(newFs); 
		  fs.addAll(fsTmp); 
		  
		  databaseService.getDatabase().setFirestations(fs);
		  
		  return databaseService.getDatabase().getFirestations().toList();
	  }
	 
	  //@PutMapping("")
	  /*  List<Firestation> updateFirestation(@RequestParam String address, @RequestParam String station) {
		  
		  Stream<Firestation> fsTmp = databaseService.getDatabase().getFirestations();
		  
		  Optional<Firestation> firestation = fsTmp.filter(p -> p.getAddress().equals(address) && p.getStation().equals(station)).findFirst();
		  
		  if(firestation.isPresent()) {
			  Firestation fsUpdate = firestation.get();
			  
			  personUpdate.setAddress(adress); 
			  personUpdate.setCity(city); 
			  personUpdate.setPhone(phone); 
			  personUpdate.setZip(zip);
			  personUpdate.setEmail(email);
			  
			  List<Person> persons = new ArrayList<>();
			  List<Person> _personsTmp = databaseService.getDatabase().getPersons().toList();
			  
			  persons.add(personUpdate); 
			  persons.addAll(_personsTmp); 
			  
			  databaseService.getDatabase().setPersons(persons);
			  
			  return databaseService.getDatabase().getPersons().toList();
			  
			} else {
				return null;
			}	 */

}
