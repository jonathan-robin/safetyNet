package com.oc.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;
import com.oc.safetynet.service.SafetyNetService;

import dto.PersonByFirestation;

@RestController
@RequestMapping("firestation")
public class FirestationsController {

	@Autowired
	private DatabaseService databaseService;
	
	@Autowired 
	private SafetyNetService snSvc;
	
	  @GetMapping("/firestations")
	  Stream<Firestation> all() {
			  return databaseService.getDatabase().getFirestations();
	  }
	  
	  
	  @PostMapping("")
	  ResponseEntity<String> addFirestation(@RequestParam String address, @RequestParam String station) {
		  
		  Firestation newFs = new Firestation(address, station); 
		  	  
		  List<Firestation> fsTmp = databaseService.getDatabase().getFirestations().toList();
		  
		  List<Firestation> fs = new ArrayList<>();
		  
		  Firestation firestation = findFirestation(address, station, fsTmp);
		  
		  if (firestation != null) 
			  return new ResponseEntity<String>("Firestation already exists.", HttpStatus.BAD_REQUEST);
		  
		  fs.add(newFs); 
		  fs.addAll(fsTmp); 
		  
		  databaseService.getDatabase().setFirestations(fs);
		  return new ResponseEntity<String>("Firestation added : " + newFs.getAddress() + " : n°" + newFs.getStation(), HttpStatus.ACCEPTED);
	  }
	  
		private Firestation findFirestation(String address, String station, List<Firestation> firestations) {
			
			Firestation firestation = firestations.stream().filter(p -> p.getAddress().equals(address) && p.getStation().equals(station))
		      .findFirst().orElse(null);
			return firestation;
		}
		
		  @PutMapping("")
		  ResponseEntity<String> updateFirestation(@RequestParam String address, @RequestParam String station) {
			  
			  Stream<Firestation> firestationTmp = databaseService.getDatabase().getFirestations();
			  Firestation firestation = firestationTmp.filter(fs -> fs.getAddress().equals(address)).findFirst().orElse(null);
			  
			  if(firestation != null) {
				  firestation.setStation(station); 
				  
				  List<Firestation> firestations = new ArrayList<>();
				  List<Firestation> _firestationsTmp = databaseService.getDatabase().getFirestations().toList();
				  
				  firestations.add(firestation); 
				  firestations.addAll(_firestationsTmp); 
				  
				  databaseService.getDatabase().setFirestations(firestations);
				  
				  return new ResponseEntity<String>("Firestation " + firestation.getAddress() + ", n°" + firestation.getStation() + " updated", HttpStatus.ACCEPTED);
				} else {
					return new ResponseEntity<String>("Can't find user to update with address: " + address, HttpStatus.ACCEPTED);
				}	
		  }
		  
		  @DeleteMapping("")
		  ResponseEntity<String> deleteFirestation(@RequestParam String address, @RequestParam String station) {
			  
			  List<Firestation> firestations = databaseService.getDatabase().getFirestations().toList();	  
			  List<Firestation> firestationsTmp = new ArrayList<>();
			  
			  Firestation firestationToDelete = findFirestation(address, station, firestations);
			  
			  if (firestationToDelete == null)
				  return new ResponseEntity<String>("Can't find firestation to delete: "+ address + ", n°" + station, HttpStatus.NOT_FOUND);
			  
			  firestations.forEach(p -> {
				  if (p.getAddress().equals(address) && p.getStation().equals(station))
					  return;
				  else 
					  firestationsTmp.add(p);
			  });
			  
			  databaseService.getDatabase().setFirestations(firestationsTmp);
			  return new ResponseEntity<String>("firestation n°" + station + ", address: " + address + " deleted", HttpStatus.ACCEPTED);
		  }	
		  

}
