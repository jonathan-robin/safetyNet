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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynet.models.Firestation;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;

import dto.DtoPersonWithMedication;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingFirestationsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetAllFirestations() throws Exception { 
		
		this.mockMvc.perform(get("/firestation/firestations"))
			.andExpect(status().isOk());		
	}
	
	@Test
	void testAddFirestations() throws Exception { 
		
		/* create a new person to insert */
		Firestation firestation = new Firestation();
		firestation.setAddress("test");
		firestation.setStation("test");

		
		/* insert new person */
		this.mockMvc.perform(post("/firestation")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("address", firestation.getAddress())
	        .param("station", firestation.getStation()));
		
		/* check if we have one more row in json */
		this.mockMvc.perform(get("/firestation/firestations"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].address", is("test")));

		
	}

}
