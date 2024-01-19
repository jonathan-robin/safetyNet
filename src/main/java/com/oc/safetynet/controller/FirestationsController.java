package com.oc.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.oc.safetynet.models.Person;
import com.oc.safetynet.service.DatabaseService;
import com.oc.safetynet.service.SafetyNetService;

import dto.PersonByFirestation;

@RestController
@RequestMapping("firestation")
public class FirestationsController {
	
	Logger logger = LoggerFactory.getLogger(MedicalsRecordController.class);

	@Autowired
	private DatabaseService databaseService;

	@GetMapping("/firestations")
	Stream<Firestation> all() {
		logger.info("/firestation/firestations [GET] FirestationsController.all() called!");
		Stream<Firestation> fs = databaseService.getDatabase().getFirestations();
		logger.info("Firestations List: {}", fs);
		return fs;
	}

	@PostMapping("")
	ResponseEntity<String> addFirestation(@RequestParam String address, @RequestParam String station) {

		logger.info("/firestation [POST] firestation.addFirestation() called!");
		
		Firestation newFs = new Firestation(address, station);

		List<Firestation> fsTmp = databaseService.getDatabase().getFirestations().toList();

		List<Firestation> fs = new ArrayList<>();

		Firestation firestation = findFirestation(address, station, fsTmp);

		if (firestation != null)
			return new ResponseEntity<String>("Firestation already exists.", HttpStatus.BAD_REQUEST);

		fs.add(newFs);
		fs.addAll(fsTmp);

		databaseService.getDatabase().setFirestations(fs);
		logger.info("new Firestations inserted: {}", fs.toString());
		return new ResponseEntity<String>("Firestation added : " + newFs.getAddress() + " : n°" + newFs.getStation(),
				HttpStatus.OK);
	}

	private Firestation findFirestation(String address, String station, List<Firestation> firestations) {

		logger.debug("function FirestationController.findFirestation() called with parameter: {}"
				+ "address: {}" + address 
				+ "station: {}" + station 
				+ "firestations: {}" + firestations ); 
		
		Firestation firestation = firestations.stream()
				.filter(p -> p.getAddress().equals(address) && p.getStation().equals(station)).findFirst().orElse(null);
		
		logger.debug("Function result firestation: {}"+ firestation);
		return firestation;
	}
	
	

	@PutMapping("")
	ResponseEntity<String> updateFirestation(@RequestParam String address, @RequestParam String station) {

		logger.info("/firestations [PUT] FirestationsController.updateFirestation() called!");
		
		Stream<Firestation> firestationTmp = databaseService.getDatabase().getFirestations();
		Firestation firestation = firestationTmp.filter(fs -> fs.getAddress().equals(address)).findFirst().orElse(null);

		if (firestation != null) {
			firestation.setStation(station);

			List<Firestation> firestations = new ArrayList<>();
			List<Firestation> _firestationsTmp = databaseService.getDatabase().getFirestations().toList();

			firestations.add(firestation);
			firestations.addAll(_firestationsTmp);

			databaseService.getDatabase().setFirestations(firestations);

			logger.info("Firestations: {}"+firestation.toString() + " updated!");
			return new ResponseEntity<String>(
					"Firestation " + firestation.getAddress() + ", n°" + firestation.getStation() + " updated",
					HttpStatus.OK);
		} else {
			logger.error("Can't find firestation to update: " + address);
			return new ResponseEntity<String>("Can't find user to update with address: " + address,
					HttpStatus.OK);
		}
	}

	@DeleteMapping("")
	ResponseEntity<String> deleteFirestation(@RequestParam String address, @RequestParam String station) {

		logger.info("/firestation [DELETE] FirestationsController.deleteFirestation() called!");
		
		List<Firestation> firestations = databaseService.getDatabase().getFirestations().toList();
		List<Firestation> firestationsTmp = new ArrayList<>();

		Firestation firestationToDelete = findFirestation(address, station, firestations);

		if (firestationToDelete == null) {
			logger.error("Can't find firestation to update: " + address + ", n°" + station);						
			return new ResponseEntity<String>("Can't find firestation to delete: " + address + ", n°" + station,
					HttpStatus.NOT_FOUND);
		}

		firestations.forEach(p -> {
			if (p.getAddress().equals(address) && p.getStation().equals(station))
				return;
			else
				firestationsTmp.add(p);
		});

		databaseService.getDatabase().setFirestations(firestationsTmp);
		logger.info("firestation n°" + station + ", address: " + address + " deleted!");
		return new ResponseEntity<String>("firestation n°" + station + ", address: " + address + " deleted",
				HttpStatus.OK);
	}

}
