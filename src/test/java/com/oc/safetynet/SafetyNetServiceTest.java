/**
 * 
 */
package com.oc.safetynet;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.SafetyNetService;

import dto.PersonByFirestation;

/**
 * 
 */

@SpringBootTest
class SafetyNetServiceTest {

	@Autowired
	SafetyNetService safetyNetService;
	
	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getPerson(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetPerson() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getMedicalRecord(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetMedicalRecord() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.oc.safetynet.service.SafetyNetService#getAddressesByStationNumber(java.lang.String)}.
	 * @throws ParseException 
	 */
	@Test
	void testGetAddressesByStationNumber(){
		
		PersonByFirestation persons = safetyNetService.getPersonsByStationNumber("1");
		Assert.notNull(persons, "No adresses found");
		
	}

}
