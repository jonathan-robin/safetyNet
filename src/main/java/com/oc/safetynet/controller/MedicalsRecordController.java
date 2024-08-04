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

import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.service.DatabaseService;
import com.oc.safetynet.service.MedicalsRecordService;

@RestController
@RequestMapping("medicalRecord")
public class MedicalsRecordController {

	Logger logger = LoggerFactory.getLogger(MedicalsRecordController.class);
	
	@Autowired
	private DatabaseService databaseService;
	
	@Autowired
	private MedicalsRecordService mrSvc;
	
	  @GetMapping("/all")
	  Stream<MedicalRecord> all() {
		  logger.info("/medicalRecord/all [GET] MedicalRecordController.all() called!");
		  Stream <MedicalRecord> mr = databaseService.getDatabase().getMedicalrecords();
		  logger.info("medicalRecords List: {}", mr);
		  return mr;
	  }
	
	  
	  @PostMapping("")
	  ResponseEntity<String> addMedicalRecord(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthdate,
			  @RequestParam List<String> medications, @RequestParam List<String> allergies) {
		  
		  logger.info("/medicalRecord [POST] MedicalRecordController.addMedicalRecord() called!");
		  MedicalRecord newMr = new MedicalRecord(firstName, lastName, allergies, birthdate, medications);
		  
		  	  
		  List<MedicalRecord> mrTmp = databaseService.getDatabase().getMedicalrecords().toList();
		  List<MedicalRecord> mr = new ArrayList<>();
		  
		  MedicalRecord medicalRecord = mrSvc.findMedicalRecord(firstName, lastName, birthdate);
		  
		  if (medicalRecord != null) 
			  return new ResponseEntity<String>("MedicalRecord already exists.", HttpStatus.BAD_REQUEST);
		  
		  mr.add(newMr); 
		  mr.addAll(mrTmp); 
		  
		  databaseService.getDatabase().setMedicalrecords(mr);
		  logger.info("new MedicalRecord inserted: {}", newMr.toString());
		  return new ResponseEntity<String>("MedicalRecord added : " + newMr.getFirstName() + newMr.getLastName(), HttpStatus.OK);
	  }
	    
	  @PutMapping("")
	  ResponseEntity<String> updateMedicalRecord(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthdate,
			  @RequestParam List<String> medications, @RequestParam List<String> allergies) {
		  
		  logger.info("/medicalRecord [PUT] MedicalRecordController.updateMedicalRecord() called!");
		  
		  Stream<MedicalRecord> medicalRecordTmp = databaseService.getDatabase().getMedicalrecords();
		  MedicalRecord medicalRecord = medicalRecordTmp.filter(mr -> mr.getLastName().equals(lastName) && mr.getFirstName().equals(firstName)).findFirst().orElse(null);
		  
		  if(medicalRecord != null) {

			  mrSvc.updateMr(medicalRecord, firstName, lastName, allergies, birthdate, medications);
			  
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
		  
		  MedicalRecord medicalRecordToDelete = mrSvc.findMedicalRecord(firstName, lastName, birthdate);
		  
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
