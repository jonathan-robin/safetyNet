package com.oc.safetynet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.service.DatabaseService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingMedicalsRecordControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	DatabaseService dbSvc;
	
	MedicalRecord mr = null;
	
	@BeforeEach
	void init() {
		mr = dbSvc.getDatabase().getMedicalrecords().toList().get(0);
	}
	
	@Test
	void testGetAllMedicalRecordsByFirestations() throws Exception { 
		
		this.mockMvc.perform(get("/medicalRecord/all"))
			.andExpect(status().isOk());		
	}
	
	@Test
	void testAddMedicalRecord() throws Exception { 
		
		/* create a new person to insert */
		MedicalRecord _mr = new MedicalRecord("test", "test", new ArrayList<>(), "test", new ArrayList<>());
		
		/* insert new person */
		this.mockMvc.perform(post("/medicalRecord")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("allergies", _mr.getAllergies().toString())
	        .param("birthdate", _mr.getBirthdate())
	        .param("firstName",_mr.getFirstName())
	        .param("lastName", _mr.getLastName())
	        .param("medications", _mr.getMedications().toString()))
		.andExpect(status().isOk());
	}
	
	@Test 
	void testUpdateMedicalRecord() throws Exception { 
		
		mr.setLastName("updatedLastName");
		
		/* find person  and udpate*/
		this.mockMvc.perform(MockMvcRequestBuilders
	         .put("/medicalRecord")
			.contentType(MediaType.APPLICATION_JSON)
			.param("allergies", mr.getAllergies().toString())
	        .param("birthdate", mr.getBirthdate())
	        .param("firstName", mr.getFirstName())
	        .param("lastName", mr.getLastName())
	        .param("medications", mr.getMedications().toString()))
		.andExpect(status().isOk());

		
	}
	
	@Test
	void testDeleteMedicalRecord() throws Exception { 
		
		/* delete new person */
		 this.mockMvc.perform(MockMvcRequestBuilders
		            .delete("/medicalRecord")
		            .contentType(MediaType.APPLICATION_JSON)
			        .param("birthdate", mr.getBirthdate())
			        .param("firstName", mr.getFirstName())
			        .param("lastName", mr.getLastName()))
		 	.andExpect(status().isOk());
		 
		 /* delete can't find */
		 this.mockMvc.perform(MockMvcRequestBuilders
		            .delete("/medicalRecord")
		            .contentType(MediaType.APPLICATION_JSON)
			        .param("birthdate", "test")
			        .param("firstName", "test")
			        .param("lastName", "test"));
		
	}
	
}
