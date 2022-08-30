package com.jrtp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrtp.entity.EligibilityDetails;
import com.jrtp.request.SearchRequest;
import com.jrtp.response.SearchResponse;
import com.jrtp.service.EligibilityService;

@RestController
public class EligiblityController {

	private static final Logger log = LoggerFactory.getLogger(EligiblityController.class);

	@Autowired
	private EligibilityService eligibilityService;

	@PostMapping("/elig/add")
	public ResponseEntity<String> addEligibility(@RequestBody EligibilityDetails elig) {
		String msg = eligibilityService.addEligibilityRecords(elig);
		log.info("Incoming elig record {}", elig);
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}

	@GetMapping(value = "/getplans", produces = "application/json")
	public ResponseEntity<List<String>> getPlans() {
		List<String> uniquePlanNames = eligibilityService.getUniquePlanNames();
		return new ResponseEntity<>(uniquePlanNames, HttpStatus.OK);
	}

	@GetMapping(value = "/getplanstatus")
	public ResponseEntity<List<String>> getPlanStatus() {
		List<String> uniquePlanNames = eligibilityService.getUniquePlanStatus();
		return new ResponseEntity<>(uniquePlanNames, HttpStatus.OK);
	}

	@GetMapping(value = "/search/{planName}/{planStatus}" )
	public ResponseEntity<List<SearchResponse>> search(@PathVariable String planName ,
			@PathVariable String planStatus) {
		SearchRequest req= new SearchRequest();
		req.setPlanName(planName);
		req.setPlanStatus(planStatus);
		
		log.info("Request object" +req);
		List<SearchResponse> searchResult = eligibilityService.search(req);
		return new ResponseEntity<>(searchResult,HttpStatus.OK);
	}

}
