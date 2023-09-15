package com.oc.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.safetynet.service.SafetyNetService;

import dto.PersonByFirestation;

@RestController
public class SafetyNetController {
	
	@Autowired
	SafetyNetService snSvc;

  @GetMapping("/firestation")
  PersonByFirestation getPersonsByFirestation(@RequestParam String stationNumber){
	  
	  PersonByFirestation dto = snSvc.getPersonsByStationNumber(stationNumber);
	  
	  return dto;
  }
		
}
