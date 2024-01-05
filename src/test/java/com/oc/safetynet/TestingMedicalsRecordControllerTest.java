package com.oc.safetynet;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynet.models.Firestation;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;

import dto.DtoPersonWithMedication;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingMedicalsRecordControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetAllMedicalRecordsByFirestations() throws Exception { 
		
		this.mockMvc.perform(get("/medicalRecord/firestations"))
			.andExpect(status().isOk());		
	
	}
	
	@Test
	void testAddMedicalRecord() throws Exception { 
		
		/* create a new person to insert */
		MedicalRecord mr = new MedicalRecord();
		mr.setAllergies(new ArrayList<>());
		mr.setBirthdate("test");
		mr.setFirstName("test");
		mr.setLastName("test");
		mr.setMedications(new ArrayList<>());

		
		/* insert new person */
		this.mockMvc.perform(post("/medicalRecord")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("allergies", mr.getAllergies().toString())
	        .param("birthdate", mr.getBirthdate())
	        .param("firstName", mr.getFirstName())
	        .param("lastName", mr.getLastName())
	        .param("medications", mr.getMedications().toString()))
		.andExpect(status().isOk());

	}
	

	
	
}
