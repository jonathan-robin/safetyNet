package com.oc.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.safetynet.models.Firestation;
import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.service.DatabaseService;

@RestController
@RequestMapping("medicalRecord")
public class MedicalsRecordController {

	Logger logger = LoggerFactory.getLogger(MedicalsRecordController.class);
	
	@Autowired
	private DatabaseService databaseService;
	
	  @GetMapping("/firestations")
	  Stream<MedicalRecord> all() {
		  logger.info("/medicalRecord/firestations [GET] MedicalRecordController.all() called!");
		  Stream <MedicalRecord> mr = databaseService.getDatabase().getMedicalrecords();
		  logger.info("medicalRecords List: {}", mr);
		  return mr;
	  }
	
	  
	  @PostMapping("")
	  ResponseEntity<String> addMedicalRecord(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthdate,
			  @RequestParam List<String> medications, @RequestParam List<String> allergies) {
		  
		  logger.info("/medicalRecord [POST] MedicalRecordController.addMedicalRecord() called!");
		  MedicalRecord newMr = new MedicalRecord();
		  
		  	  
		  List<MedicalRecord> mrTmp = databaseService.getDatabase().getMedicalrecords().toList();
		  List<MedicalRecord> mr = new ArrayList<>();
		  
		  MedicalRecord medicalRecord = findMedicalRecord(firstName, lastName, birthdate, mrTmp);
		  		  if (medicalRecord != null) 
			  return new ResponseEntity<String>("MedicalRecord already exists.", HttpStatus.BAD_REQUEST);
		  
		  newMr.setFirstName(firstName);
		  newMr.setLastName(lastName);
		  newMr.setAllergies(allergies);
		  newMr.setBirthdate(birthdate);
		  newMr.setMedications(medications);
		  
		  mr.add(newMr); 
		  mr.addAll(mrTmp); 
		  
		  databaseService.getDatabase().setMedicalrecords(mr);
		  logger.info("new MedicalRecord inserted: {}", newMr.toString());
		  return new ResponseEntity<String>("MedicalRecord added : " + newMr.getFirstName() + newMr.getLastName(), HttpStatus.OK);
	  }
	  
	  
	  public MedicalRecord findMedicalRecord(String firstName, String lastName, String birthdate, List<MedicalRecord> medicalRecords) {
		  return medicalRecords.stream().filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName) && mr.getBirthdate().equals(birthdate)).findFirst().orElse(null);
	  }
	  
	  @PutMapping("")
	  ResponseEntity<String> updateMedicalRecord(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthdate,
			  @RequestParam List<String> medications, @RequestParam List<String> allergies) {
		  
		  logger.info("/medicalRecord [PUT] MedicalRecordController.updateMedicalRecord() called!");
		  
		  Stream<MedicalRecord> medicalRecordTmp = databaseService.getDatabase().getMedicalrecords();
		  MedicalRecord medicalRecord = medicalRecordTmp.filter(mr -> mr.getLastName().equals(lastName) && mr.getFirstName().equals(firstName)).findFirst().orElse(null);
		  
		  if(medicalRecord != null) {
			  
			  medicalRecord.setFirstName(firstName);
			  medicalRecord.setLastName(lastName);
			  medicalRecord.setAllergies(allergies);
			  medicalRecord.setBirthdate(birthdate);
			  medicalRecord.setMedications(medications);
			  
			  List<MedicalRecord> medicalRecords = new ArrayList<>();
			  List<MedicalRecord> _medicalRecords = databaseService.getDatabase().getMedicalrecords().toList();
			  
			  medicalRecords.add(medicalRecord); 
			  medicalRecords.addAll(_medicalRecords); 
			  
			  logger.info("MedicalRecord: {}"+medicalRecord.toString() + " updated!");
			  databaseService.getDatabase().setMedicalrecords(medicalRecords);
			  
			  return new ResponseEntity<String>("MedicalRecord for: " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " updated", HttpStatus.OK);
			} else {
				logger.error("Can't find medicalRecord to update: " + firstName + " " +lastName);
				return new ResponseEntity<String>("Can't find medicalRecord to update with name: " + lastName + " " + firstName , HttpStatus.OK);
			}	
	  }
	  
	  @DeleteMapping("")
	  ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthdate) {
		  
		  logger.info("/person [DELETE] MedicalRecordController.deleteMedicalRecord() called!");
		  List<MedicalRecord> medicalRecords = databaseService.getDatabase().getMedicalrecords().toList();	  
		  List<MedicalRecord> medicalRecordTmp = new ArrayList<>();
		  
		  MedicalRecord medicalRecordToDelete = findMedicalRecord(firstName, lastName, birthdate, medicalRecords);
		  
		  if (medicalRecordToDelete == null) {
			  logger.error("Can't find medicalRecord to update: " + firstName + " " +lastName);			  
			  return new ResponseEntity<String>("Can't find medicalRecord to delete for user "+ lastName + " " + firstName, HttpStatus.NOT_FOUND);
		  }
		  
		  medicalRecords.forEach(p -> {
			  if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName) && p.getBirthdate().equals(birthdate))
				  return;
			  else 
				  medicalRecordTmp.add(p);
		  });
		  
		  databaseService.getDatabase().setMedicalrecords(medicalRecordTmp);
		  logger.info("MedicalRecord for : " + lastName + " " + firstName + " deleted!");
		  return new ResponseEntity<String>("medicalRecord for " + lastName + " " + firstName + " deleted", HttpStatus.OK);
	  }	
	  
}
