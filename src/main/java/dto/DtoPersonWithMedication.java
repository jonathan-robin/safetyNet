package dto;

import java.util.List;

import com.oc.safetynet.models.MedicalRecord;
import com.oc.safetynet.models.Person;

public class DtoPersonWithMedication {

	Person person;
	MedicalRecord medicalRecord;
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Person getPerson() {
		return this.person;
	}
	
	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	public MedicalRecord getMedicalRecord() {
		return this.medicalRecord;
	}
	

	
}
