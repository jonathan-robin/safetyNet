package com.oc.safetynet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;
import com.oc.safetynet.service.SafetyNetService;

import dto.ChildsByAddress;
import dto.PersonByAddress;
import dto.PersonByFirestation;

@RestController
public class SafetyNetController {

	@Autowired
	SafetyNetService snSvc;
	
	@Autowired
	DatabaseService databaseService;

	@GetMapping("/firestation")
	PersonByFirestation getPersonsByFirestation(@RequestParam String stationNumber) {

		PersonByFirestation dto = snSvc.getPersonsByStationNumber(stationNumber);

		return dto;
	}
	
	@GetMapping("/childAlert")
	ChildsByAddress getChildsByAddress(@RequestParam String address) {

		ChildsByAddress dto = snSvc.getHomeByAddress(address);
		
		if(dto.getChilds().size() > 0)
			return dto;
		
		else 
			return null;
		
	}
	
	@GetMapping("/phoneAlert")
	List<String> getPhonesNumberByStationNumber(@RequestParam String firestation){
		
		List<String> phonesNumber = snSvc.getPhoneNumberByStationNumber(firestation);
		return phonesNumber;
		
	}
	
	@GetMapping("/fire")
	List<PersonByAddress> getPersonByAddress(@RequestParam String address){
		
		List<PersonByAddress> dto = snSvc.getPersonByAddress(address);
		return dto;
	}	
	
	@GetMapping("/flood")
	Map<String, List<PersonByAddress>> getPersonsByStation(@RequestParam List<String> stations){
		
		List<String> addresses = new ArrayList<>(); 
		List<List<String>> tmp = new ArrayList<>();
		
		stations.forEach(station -> tmp.add(snSvc.getAddressesByStationNumber(station)));
		
		addresses = tmp.stream()
	        .flatMap(List::stream)
	        .collect(Collectors.toList());
				
		Map<String, List<PersonByAddress>> dto = new HashMap<>();
		
		addresses.forEach(address -> dto.put(address, snSvc.getPersonByAddress(address)));
		
		return dto;
		
	}

	
	@GetMapping("/communityEmail")
	ResponseEntity<List<String>> getEmailsByCity(@RequestParam String city){
		
		Stream<Person> persons = databaseService.getDatabase().getPersons();
		List<Person> personTmp = persons.filter(person -> person.getCity().equals(city)).toList();
		
		List<String> emails = new ArrayList<>(); 
		personTmp.forEach(person -> emails.add(person.getEmail())); 
		
		return new ResponseEntity<List<String>>(emails, HttpStatus.OK);
	}
	
}
