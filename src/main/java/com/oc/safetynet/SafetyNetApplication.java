package com.oc.safetynet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynet.service.DatabaseService;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

@ComponentScan("com.oc.safetynet")
@SpringBootApplication
public class SafetyNetApplication {

	@Autowired
	private DatabaseService databaseService;
	
	public static void main(String[] args) {
	
		SpringApplication.run(SafetyNetApplication.class, args);

	}
	
	@PostConstruct
	public void init() { 
		databaseService.loadDatabase();
	}

}
