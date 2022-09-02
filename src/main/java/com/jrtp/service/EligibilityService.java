package com.jrtp.service;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jrtp.entity.EligibilityDetails;
import com.jrtp.request.SearchRequest;
import com.jrtp.response.SearchResponse;

public interface EligibilityService {

	public String addEligibilityRecords(EligibilityDetails elg);
	
	public List<String> getUniquePlanNames();   //same table data 1st drop down
	
	public List<String> getUniquePlanStatus();  //same table data 2nd drop down
	
	public List<SearchResponse> search(SearchRequest request); //request  data is dynamic
	
	public List<SearchResponse> searchRecords(SearchRequest request);
	public void generateXLS(HttpServletResponse response) throws Exception;
    //public HttpServletResponse generateXLS(); same as above java is pass by reference
	
	public void geneartePDF(HttpServletResponse response) throws Exception; // download file added to response object
	
	
}
