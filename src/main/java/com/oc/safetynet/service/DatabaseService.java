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
            
            System.out.println(file.getAbsolutePath());

            // Lire le fichier JSON et le mapper vers une classe ou une structure de données appropriée
            
            this.database = objectMapper.readValue(file, Database.class);
            
            System.out.println(database.getPersons().findFirst());
            
            Person firstPerson = database.getPersons().findFirst().orElseThrow();
            System.out.println(firstPerson.getFullName());

            // Faire quelque chose avec l'objet obtenu
            //System.out.println(persons);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public Database getDatabase() {
		return this.database;
	}
	
}
