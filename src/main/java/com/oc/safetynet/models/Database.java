package com.oc.safetynet.models;

import java.util.List;
import java.util.stream.Stream;

public class Database {
	
	private List<Person> persons; 
	private List<Firestation> firestations; 
	private List<MedicalRecord> medicalrecords;
	
	public Stream<Person> getPersons() {
		return persons.stream();
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public Stream<Firestation> getFirestations() {
		return firestations.stream();
	}
	public void setFirestations(List<Firestation> firestations) {
		this.firestations = firestations;
	}
	public Stream<MedicalRecord> getMedicalrecords() {
		return medicalrecords.stream();
	}
	public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	} 
}
