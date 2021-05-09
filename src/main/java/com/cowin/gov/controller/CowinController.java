package com.cowin.gov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cowin.gov.dto.DistrictResponse;
import com.cowin.gov.service.CowinService;

@RestController
public class CowinController {
	
	@Autowired
	CowinService service;
	
	@GetMapping("/cowin/slots/getAll")
	public DistrictResponse getAllSlots() {
		return service.getAll();
	}

}
