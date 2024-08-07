package com.oc.safetynet;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynet.dto.DtoPersonWithMedication;
import com.oc.safetynet.models.Firestation;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingSafetyNetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private Person person; 
	private Firestation fs;
	
	@Autowired
	private DatabaseService dbSvc; 
	
	@BeforeEach
	public void init() { 
		this.person = dbSvc.getDatabase().getPersons().toList().get(0);
		this.fs = dbSvc.getDatabase().getFirestations().toList().get(0);
	}
	
	@Test
	void testGetPersonsByFirestation() throws Exception { 
			
		this.mockMvc.perform(get("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
		        .param("stationNumber", fs.getStation()))
			.andExpect(status().isOk());
	}
	

	@Test
	void testGetChildsByAddress() throws Exception { 
		
		this.mockMvc.perform(get("/childAlert")
				.contentType(MediaType.APPLICATION_JSON)
		        .param("address", fs.getAddress()))
			.andExpect(status().isOk());
		
		/* test no child at this address */
		this.mockMvc.perform(get("/childAlert")
				.contentType(MediaType.APPLICATION_JSON)
		        .param("address", "null"))
			.andExpect(status().isOk());
		
	}
	
	@Test
	void testGetPhonesNumberByStationNumber() throws Exception { 
		
		this.mockMvc.perform(get("/phoneAlert")
				.contentType(MediaType.APPLICATION_JSON)
		        .param("firestation", fs.getStation()))
			.andExpect(status().isOk());
		
	}
	
	@Test
	void testGetPersonByAddress() throws Exception { 
		this.mockMvc.perform(get("/fire")
				.contentType(MediaType.APPLICATION_JSON)
		        .param("address", fs.getAddress()))
			.andExpect(status().isOk());
	}
	
	@Test 
	void testGetPersonsByStation() throws Exception { 
		
		List<String> stations = new ArrayList<>(); 
		stations.add("1");
		stations.add("2");
		
		this.mockMvc.perform(get("/flood")
				.contentType(MediaType.APPLICATION_JSON)
		        .param("stations", stations.toString()))
			.andExpect(status().isOk());
		
	}
	
	@Test
	void testGetEmailsByCity() throws Exception {
		
		this.mockMvc.perform(get("/communityEmail")
				.contentType(MediaType.APPLICATION_JSON)
		        .param("city", person.getCity()))
			.andExpect(status().isOk());
		
	}
	
	
	
		
		
}
