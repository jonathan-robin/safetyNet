package com.oc.safetynet;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oc.safetynet.dto.ChildsByAddress;
import com.oc.safetynet.dto.PersonByAddress;
import com.oc.safetynet.dto.PersonByFirestation;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;
import com.oc.safetynet.service.SafetyNetService;

/**
 * 
 */

@SpringBootTest
class SafetyNetServiceTest {

	@Autowired
	DatabaseService dbSvc;
	
	/* pom.xml clic droit run maven test */
	@Autowired
	SafetyNetService safetyNetService;
	
	Person personCopy = null;
	
	
	@BeforeEach
	void init() throws Exception { 
		personCopy = dbSvc.getDatabase().getPersons().toList().get(0);
	}
	
	
	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getPersons(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetPerson() {
		
		List<Person> person = safetyNetService.getPersons(this.personCopy.getLastName(),this.personCopy.getFirstName());
		assertTrue(person.get(0).getFullName().equals(this.personCopy.getFirstName() + " " + this.personCopy.getLastName()));

		
		List<Person> personNull = safetyNetService.getPersons(null, null);
		assertTrue(personNull.size() == 0);
	}

	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getMedicalRecord(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetMedicalRecord() {
		
		MedicalRecord mr = safetyNetService.getMedicalRecord(this.personCopy.getLastName(), this.personCopy.getFirstName());
		
		assertNotNull(mr.getBirthdate());

	}

	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getAddressesByStationNumber(java.lang.String)}.
	 * @throws ParseException 
	 */
	@Test
	void testGetAddressesByStationNumber(){

		PersonByFirestation persons = safetyNetService.getPersonsByStationNumber("1");

		assertTrue(persons.getMajorsCount() == 5);
		assertTrue(persons.getMinorsCount() == 1);
		
	}
	
	@Test 
	void testGetPersonByAddress() {
		
		List<PersonByAddress> persons = safetyNetService.getPersonByAddress(this.personCopy.getAddress()); 
		assertTrue(persons.size() > 0);
		
		for (PersonByAddress personByAddress : persons) {
			assertTrue(personByAddress.getName() != null);
		}
		
	}
	
	@Test
	void testGetPhoneNumberByStationNumber() {
		List<String> phoneNumber = safetyNetService.getPhoneNumberByStationNumber("3");

		assertTrue(phoneNumber.size() > 0);
	}

	@Test
	void testGetHomeByAddress() {
		ChildsByAddress homes = safetyNetService.getHomeByAddress(this.personCopy.getAddress());
		 
		assertTrue(homes.getAdults().size() > 0); 
		assertTrue(homes.getChilds().size() > 0);
	}
	
	@Test
	void testGetAddressByStationNumber() { 
		List<String> addresses = safetyNetService.getAddressesByStationNumber("3"); 
		
		assertTrue(addresses.size() > 0);
		
	}


	
}
