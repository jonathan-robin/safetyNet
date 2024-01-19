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
	
	public void loadDatabase() { 
		 
		ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Charger le fichier JSON
            File file = new File("src/main/resources/data.json");

            // Lire le fichier JSON et le mapper vers une classe ou une structure de données appropriée
            
            this.database = objectMapper.readValue(file, Database.class);
  
            Person firstPerson = database.getPersons().findFirst().orElseThrow();


        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public Database getDatabase() {
		return this.database;
	}
	
}
