package dto;

import java.util.List;

import com.oc.safetynet.models.Person;

public class PersonByFirestation {
	
	private List<Person> persons; 
	private Integer majorsCount; 
	private Integer minorsCount;
	
	public List<Person> getPersons() {
		return persons;
	}

	public Integer getMajorsCount() {
		return majorsCount;
	}

	public Integer getMinorsCount() {
		return minorsCount;
	}
	
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public void setMajorsCount(Integer majorsCount) {
		this.majorsCount = majorsCount; 
	}
	public void setMinorsCount(Integer minorsCount) {
		this.minorsCount = minorsCount;
	}
}
