package com.oc.safetynet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.safetynet.dto.ChildsByAddress;
import com.oc.safetynet.dto.PersonByAddress;
import com.oc.safetynet.dto.PersonByFirestation;
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;
import com.oc.safetynet.service.SafetyNetService;

@RestController
public class SafetyNetController {

	Logger logger = LoggerFactory.getLogger(SafetyNetController.class);

	@Autowired
	SafetyNetService snSvc;

	@Autowired
	DatabaseService databaseService;

	@GetMapping("/firestation")
	PersonByFirestation getPersonsByFirestation(@RequestParam String stationNumber) {
		logger.info("/firestation called!");
		PersonByFirestation dto = snSvc.getPersonsByStationNumber(stationNumber);
		logger.info("persons List: {}", dto);
		return dto;
	}

	@GetMapping("/childAlert")
	ChildsByAddress getChildsByAddress(@RequestParam String address) {

		logger.info("/childAlert called!");
		ChildsByAddress dto = snSvc.getHomeByAddress(address);
		logger.info("ChildsByAddress: {}", dto);

		return dto;

	}

	@GetMapping("/phoneAlert")
	List<String> getPhonesNumberByStationNumber(@RequestParam String firestation) {

		logger.info("/phoneAlert called!");
		List<String> phonesNumber = snSvc.getPhoneNumberByStationNumber(firestation);
		logger.info("phonesNumber: {}", phonesNumber);

		return phonesNumber;

	}

	@GetMapping("/fire")
	List<PersonByAddress> getPersonByAddress(@RequestParam String address) {

		logger.info("/fire called!");
		List<PersonByAddress> dto = snSvc.getPersonByAddress(address);
		logger.info("PersonByAddress: {}", dto);

		return dto;
	}

	@GetMapping("/flood")
	Map<String, List<PersonByAddress>> getPersonsByStation(@RequestParam List<String> stations) {

		logger.info("/flood called!");
		List<String> addresses = new ArrayList<>();
		List<List<String>> tmp = new ArrayList<>();

		stations.forEach(station -> tmp.add(snSvc.getAddressesByStationNumber(station)));

		addresses = tmp.stream().flatMap(List::stream).collect(Collectors.toList());

		Map<String, List<PersonByAddress>> dto = new HashMap<>();

		addresses.forEach(address -> dto.put(address, snSvc.getPersonByAddress(address)));
		logger.info("Persons By StationNumber: {}", dto);

		return dto;

	}

	@GetMapping("/communityEmail")
	ResponseEntity<List<String>> getEmailsByCity(@RequestParam String city) {

		logger.info("/communityEmail called!");
		Stream<Person> persons = databaseService.getDatabase().getPersons();
		List<Person> personTmp = persons.filter(person -> person.getCity().equals(city)).toList();

		List<String> emails = new ArrayList<>();
		personTmp.forEach(person -> emails.add(person.getEmail()));
		logger.info("Emails: {}", emails);

		return new ResponseEntity<List<String>>(emails, HttpStatus.OK);
	}

}
