/**
 * 
 */
package com.oc.safetynet;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.SafetyNetService;

import dto.ChildsByAddress;
import dto.PersonByAddress;
import dto.PersonByFirestation;

/**
 * 
 */

@SpringBootTest
class SafetyNetServiceTest {

	/* pom.xml clic droit run maven test */
	@Autowired
	SafetyNetService safetyNetService;
	
	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getPersons(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetPerson() {
		
		List<Person> person = safetyNetService.getPersons("Boyd", "Jacob");
		
		System.out.print(person.get(0).getFullName());
		assertTrue(person.get(0).getFullName().equals("Jacob Boyd"));
		
		List<Person> personNull = safetyNetService.getPersons(null, null);
		assertTrue(personNull.size() == 0);
		
		
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getMedicalRecord(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetMedicalRecord() {
		
		MedicalRecord mr = safetyNetService.getMedicalRecord("Boyd", "Jacob");
		
		assertTrue(mr.getBirthdate().equals("03/06/1989"));
		

		
		//fail("Not yet implemented");
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
		
		List<PersonByAddress> persons = safetyNetService.getPersonByAddress("1509 Culver St"); 
		assertTrue(persons.size() == 5);
		
		for (PersonByAddress personByAddress : persons) {
			assertTrue(personByAddress.getName() != null);
		}
		//assertTrue(persons.)
		
	}
	
	@Test
	void testGetPhoneNumberByStationNumber() {
		List<String> phoneNumber = safetyNetService.getPhoneNumberByStationNumber("3");

		assertTrue(phoneNumber.size() == 7);
		for (String number : phoneNumber) {
			assertTrue(number.startsWith("841"));
		}
	}

	@Test
	void testGetHomeByAddress() {
		ChildsByAddress homes = safetyNetService.getHomeByAddress("1509 Culver St");
		 
		assertTrue(homes.getAdults().size() == 3); 
		assertTrue(homes.getChilds().size() == 2);
	}
	
	@Test
	void testGetAddressByStationNumber() { 
		List<String> addresses = safetyNetService.getAddressesByStationNumber("3"); 
		
		assertTrue(addresses.size() > 0);
		
	}


	
}
