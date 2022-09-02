package com.jrtp.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		List<String> uniquePlanstatus = eligibilityService.getUniquePlanStatus();
		return new ResponseEntity<>(uniquePlanstatus, HttpStatus.OK);
	}

	@GetMapping(value = "/search/{planName}/{planStatus}")
	public ResponseEntity<List<SearchResponse>> search(@PathVariable String planName, @PathVariable String planStatus) {
		SearchRequest req = new SearchRequest();
		req.setPlanName(planName);
		req.setPlanStatus(planStatus);

		log.info("Request object" + req);
		List<SearchResponse> searchResult = eligibilityService.search(req);
		return new ResponseEntity<>(searchResult, HttpStatus.OK);
	}

	@GetMapping(value = "/search/{planName}/{planStatus}/{planStartDate}/{planEndDate}")
	public ResponseEntity<List<SearchResponse>> searchRecords(@PathVariable String planName,
			@PathVariable String planStatus,
			@PathVariable("planStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate planStartDate,
			@PathVariable("planEndDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate planEndDate) {
		SearchRequest req = new SearchRequest();
		req.setPlanName(planName);
		req.setPlanStatus(planStatus);
		req.setPlanEndDate(planEndDate);
		req.setPlanStartDate(planStartDate);

		log.info("Request object" + req);

		List<SearchResponse> searchRecords = eligibilityService.searchRecords(req);

		return new ResponseEntity<>(searchRecords, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/search")  //Get request will not have request body hence we not go for 
	public ResponseEntity<List<SearchResponse>> searchRecords(@RequestBody SearchRequest request) {
		
		log.info("Request object" + request);

		List<SearchResponse> searchRecords = eligibilityService.searchRecords(request);

		return new ResponseEntity<>(searchRecords, HttpStatus.OK);
	}


	@GetMapping("/downloadxls") // 
	public ResponseEntity<String> downloadXls(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=eligdata.xls";
        
        response.setHeader(headerKey, headerValue);
		
		eligibilityService.generateXLS(response);

		return new ResponseEntity<>("downloaded xls successfully", HttpStatus.OK);
	}
	
	@GetMapping("/pdf")
	public void pdfReport(HttpServletResponse response) throws Exception
	{
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=eligdata.pdf";
        response.setHeader(headerKey, headerValue);  
        
        eligibilityService.geneartePDF(response);
        
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
