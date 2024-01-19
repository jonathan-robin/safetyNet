package com.oc.safetynet.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
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

import dto.HomeByAddress;
import dto.ChildsByAddress;
import dto.PersonByAddress;
import dto.PersonByFirestation;

@Service
public class SafetyNetService {

	@Autowired
	DatabaseService dbService; 
	
	
	
	public List<Person> getPersons(String lastName, String firstName){
		Stream<Person> persons = dbService.getDatabase().getPersons();
		
		if (firstName == null)
			return persons.filter(p -> p.getLastName().equals(lastName)).toList();				
		
		return persons.filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).toList();	
	}
	
	public MedicalRecord getMedicalRecord(String lastName, String firstName) {
		Stream<MedicalRecord> medicalRecords = dbService.getDatabase().getMedicalrecords();
		return medicalRecords.filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);	
	}
	
	public String getStationNumberByAddress(String address) {
		Stream<Firestation> firestations = dbService.getDatabase().getFirestations();
		return firestations.filter(p -> p.getAddress().equals(address)).findFirst().orElse(null).getStation();
	}
	
	public List<String> getAddressesByStationNumber(String stationNumber) { 
		
		List<String> addresses = new ArrayList<>(); 
		Stream<Firestation> firestations = dbService.getDatabase().getFirestations();
		
		firestations.forEach(firestation -> {
			if (firestation.getStation().equals(stationNumber) && !addresses.contains(firestation.getAddress()))
				addresses.add(firestation.getAddress());
		});
		
		return addresses;
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
			//person.setMedicalRecord(mr);
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

		    return diff;
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ChildsByAddress getHomeByAddress(String address) {
		
		List<String> addresses = new ArrayList<>(); 
		addresses.add(address);
		ChildsByAddress dto = new ChildsByAddress();
		
		List<Person> personsByAddress = this.getPersonsByAdresses(addresses);
		
        List<HomeByAddress> parentTmp = new ArrayList<>(); 
        List<HomeByAddress> childTmp = new ArrayList<>();
		
		for (Person person : personsByAddress) {
			
			HomeByAddress personTmp = new HomeByAddress();
			
			MedicalRecord mr = this.getMedicalRecord(person.getLastName(), person.getFirstName());
			String birthdate = mr.getBirthdate();
					
			Integer age = this.getAgeByBirthdate(birthdate);
			
			personTmp.setAge(age);
			personTmp.setName(person.getLastName());
			personTmp.setFirstName(person.getFirstName());
			
			if (age > 18) 
				parentTmp.add(personTmp);
			
			else 
				childTmp.add(personTmp);
			
		}
		
		dto.setAdults(parentTmp);
		dto.setChilds(childTmp);
		return dto;
		
	}

	public List<String> getPhoneNumberByStationNumber(String stationNumber){ 
		
		List<Person> persons = this.getPersonsByStationNumber(stationNumber).getPersons();
		
		List<String> phonesNumber = new ArrayList<>(); 
		
		for (Person person : persons) {
			
			if (!phonesNumber.contains(person.getPhone())) 
				phonesNumber.add(person.getPhone());
			
			
		}
		return phonesNumber;
	}
	
	public List<PersonByAddress> getPersonByAddress(String address){
		
		List<String> addresses = new ArrayList<>(); 
		addresses.add(address);
		
		List<Person> persons = this.getPersonsByAdresses(addresses); 
		
		List<PersonByAddress> dto = new ArrayList<>();
		
		for (Person person : persons) {
			
			MedicalRecord mr = this.getMedicalRecord(person.getLastName(), person.getFirstName());
			
			PersonByAddress personByAddress = new PersonByAddress();
			
			personByAddress.setAge(mr.getBirthdate());
			personByAddress.setAllergies(mr.getAllergies());
			personByAddress.setMedications(mr.getMedications());
			personByAddress.setName(person.getFullName());
			personByAddress.setPhoneNumber(person.getPhone());
			personByAddress.setStationNumber(this.getStationNumberByAddress(address));
			
			dto.add(personByAddress);
		}
		
		return dto;
		
	}

	
	
}
