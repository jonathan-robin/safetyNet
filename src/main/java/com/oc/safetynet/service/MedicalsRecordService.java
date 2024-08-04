package com.oc.safetynet.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.safetynet.models.MedicalRecord;

@Service
public class MedicalsRecordService {
	
	@Autowired
	private DatabaseService dbSvc;
	
    public MedicalRecord findMedicalRecord(String firstName, String lastName, String birthdate) {
	  Stream<MedicalRecord> mrs = dbSvc.getDatabase().getMedicalrecords();
	  return mrs.filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName) && mr.getBirthdate().equals(birthdate)).findFirst().orElse(null);
    } 
	  
    public MedicalRecord updateMr(MedicalRecord mr, String firstName, String lastName, List<String> allergies, String birthdate, List<String> medications) {
	  
	  mr.setFirstName(firstName);
	  mr.setLastName(lastName);
	  mr.setAllergies(allergies);
	  mr.setBirthdate(birthdate);
	  mr.setMedications(medications);
	  
	  return mr;
   }
}
