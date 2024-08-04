package com.oc.safetynet.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oc.safetynet.models.Firestation;

@Service
public class FirestationsService {

	Logger logger = LoggerFactory.getLogger(FirestationsService.class);

	public Firestation findFirestation(String address, String station, List<Firestation> firestations) {

		logger.debug("function FirestationController.findFirestation() called with parameter: {}"
				+ "address: {}" + address 
				+ "station: {}" + station 
				+ "firestations: {}" + firestations ); 
		
		Firestation firestation = firestations.stream()
				.filter(p -> p.getAddress().equals(address) && p.getStation().equals(station)).findFirst().orElse(null);
		
		logger.debug("Function result firestation: {}"+ firestation);
		return firestation;
	}
	
}
