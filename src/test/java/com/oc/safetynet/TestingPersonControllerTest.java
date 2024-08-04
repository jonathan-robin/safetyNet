package com.oc.safetynet;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;

@SpringBootTest
@AutoConfigureMockMvc
class TestingPersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	Person person = null;
	
	@Autowired 
	private DatabaseService dbSvc;

    @BeforeEach
    public void init() {
    	this.person = dbSvc.getDatabase().getPersons().toList().get(0);
    }
	
	@Test
	void testGetAllPerson() throws Exception {
		
		this.mockMvc.perform(get("/person/all"))
			.andExpect(status().isOk());		
	}
	
	@Test
	void testAddPerson() throws Exception { 
		
		/* create a new person to insert */
		Person person = new Person();
		person.setFirstName("test");
		person.setLastName("test");
		person.setEmail("test");
		person.setAddress("test");
		person.setCity("test");
		person.setZip(1);
		person.setPhone("000");
		
		/* insert new person */
		this.mockMvc.perform(post("/person")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("firstName", person.getFirstName())
	        .param("lastName", person.getLastName())
	        .param("email", person.getEmail())
	        .param("address", person.getAddress())
	        .param("city", person.getCity())
	        .param("zip", person.getZip().toString())
	        .param("phone", person.getPhone()));
		
		/* check if we have one more row in json */
		this.mockMvc.perform(get("/person/all"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].lastName", is("test")));
	
	}
	
	@Test
	void testGetPersonInfo() throws Exception { 

		/* insert new person */
		this.mockMvc.perform(get("/person/personInfo")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("firstName", person.getFirstName())
	        .param("lastName", person.getLastName()))
		.andExpect(status().isOk());
	}
	

	@Test 
	void testUpdatePerson() throws Exception { 
		
		person.setEmail("testingUpdateEmail");
		
		/* find person  and udpate*/
		this.mockMvc.perform(MockMvcRequestBuilders
	         .put("/person")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("firstName", person.getFirstName())
	        .param("lastName", person.getLastName())
	        .param("email", person.getEmail())
	        .param("address", person.getAddress())
	        .param("city", person.getCity())
	        .param("zip", person.getZip().toString())
	        .param("phone", person.getPhone()))
		.andExpect(status().isOk());
		
	}
	
	@Test
	void testDeletePerson() throws Exception { 
		
		/* delete new person */
		 this.mockMvc.perform(MockMvcRequestBuilders
		            .delete("/person")
		            .contentType(MediaType.APPLICATION_JSON)
			        .param("firstName", person.getFirstName())
			        .param("lastName", person.getLastName()))
		 	.andExpect(status().isOk());
		
	}
	
}
