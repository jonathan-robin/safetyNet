package com.oc.safetynet.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Calendar;
import static java.util.Calendar.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.safetynet.models.Firestation;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;

import dto.PersonByFirestation;

@Service
public class SafetyNetService {

	@Autowired
	DatabaseService dbService; 
	
	
	
	public Person getPerson(String lastName, String firstName){
		Stream<Person> persons = dbService.getDatabase().getPersons();
		return persons.filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);	
	}
	
	public MedicalRecord getMedicalRecord(String lastName, String firstName) {
		Stream<MedicalRecord> medicalRecords = dbService.getDatabase().getMedicalrecords();
		return medicalRecords.filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);	
	}
	
	public PersonByFirestation getPersonsByStationNumber(String stationNumber){
		Stream<Firestation> firestations = dbService.getDatabase().getFirestations();
		List<String> addresses = new ArrayList<>();
		
		firestations.forEach(fs -> {
			if (fs.getStation().equals(stationNumber))
				addresses.add(fs.getAddress());
		});
		
		List<Person> persons = getPersonsByAdresses(addresses);
		
		PersonByFirestation personsByAge = getPersonByAge(persons);
		
		return personsByAge;
	}
	
	public List<Person> getPersonsByAdresses(List<String> adresses){
		Stream<Person> persons = dbService.getDatabase().getPersons();
		
		List<Person> personsTmp = persons.filter(p -> adresses.contains(p.getAddress())).toList();
		return personsTmp;
	}
	
	public PersonByFirestation getPersonByAge(List<Person> persons){
		
		Integer majorsCount = 0; 
		Integer minorsCount = 0;
		
		for (Person person : persons) {
			
			MedicalRecord mr = getMedicalRecord(person.getLastName(), person.getFirstName());
			person.setMedicalRecord(mr);
			Integer personAge = getAgeByBirthdate(mr.getBirthdate());
			
			if (personAge > 18)
				majorsCount++;
			else
				minorsCount++;
		}
		
		PersonByFirestation dto = new PersonByFirestation(); 
		dto.setPersons(persons);
		dto.setMajorsCount(majorsCount);
		dto.setMinorsCount(minorsCount);
		return dto;		
	}
	
	
	public Integer getAgeByBirthdate(String birthdate) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY", Locale.ENGLISH);
		
		Date bd = null;
		
		try {
			bd = formatter.parse(birthdate);
			
			Date now = new Date();
			
		    Calendar calBd = Calendar.getInstance(Locale.US);
		    Calendar calNow = Calendar.getInstance(Locale.US);
		    calBd.setTime(bd);
		    calNow.setTime(now);

		    int diff = calNow.get(YEAR) - calBd.get(YEAR);
		    
		    System.out.println(diff);
		    return diff;
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
	
}
