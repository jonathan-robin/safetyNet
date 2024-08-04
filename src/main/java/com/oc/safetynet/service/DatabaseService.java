package com.oc.safetynet.service;

import java.io.File;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynet.models.Database;
import com.oc.safetynet.models.Person;

@Service
public class DatabaseService {

	private Database database;
	
	public void loadDatabase() throws Exception { 
		 
		ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = new File("src/main/resources/data.json");
            this.database = objectMapper.readValue(file, Database.class);

        } catch (Exception e) {
        	throw e;
        }
	}
	
	
	public Database getDatabase() {
		return this.database;
	}
	
	public Database setDatabase(Database db) { 
		return this.database = db;
	}
	
}
