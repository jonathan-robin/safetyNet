package dto;

import java.util.List;

public class PersonByAddress {
		
		private String stationNumber;
		private String age; 
		private String name; 
		private String phoneNumber;
		private List<String> medications; 
		private List<String> allergies;
		
		
		public String getStationNumber() {
			return stationNumber;
		}
		public void setStationNumber(String stationNumber) {
			this.stationNumber = stationNumber;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public List<String> getMedications() {
			return medications;
		}
		public void setMedications(List<String> medications) {
			this.medications = medications;
		}
		public List<String> getAllergies() {
			return allergies;
		}
		public void setAllergies(List<String> allergies) {
			this.allergies = allergies;
		} 
		

	

}
