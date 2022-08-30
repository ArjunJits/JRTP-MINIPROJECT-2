package com.jrtp.serviceimpl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrtp.constants.AppConstants;
import com.jrtp.entity.EligibilityDetails;
import com.jrtp.repo.EligibilityRepo;
import com.jrtp.request.SearchRequest;
import com.jrtp.response.SearchResponse;
import com.jrtp.service.EligibilityService;

@Service
public class EligibilityServiceImpl implements EligibilityService {
	private static final Logger log = LoggerFactory.getLogger(EligibilityServiceImpl.class);
	@Autowired
	private EligibilityRepo eligibilityRepo;

	@Override
	public String addEligibilityRecords(EligibilityDetails elg) {
		EligibilityDetails save = eligibilityRepo.save(elg);
		if (save.getEligId() != null) {
			log.info("Eligibilty Record saved success {}", elg);
			return AppConstants.ELIGBILTY_SAVED;
		} else {
			log.info("Eligibilty Record insertion failed {}", elg);
			return AppConstants.ELIGBILTY_NOT_SAVED;
		}
	}

	@Override
	public List<String> getUniquePlanNames() {
		List<String> uniquePlanNames = eligibilityRepo.getUniquePlanNames();
		return uniquePlanNames;
	}

	@Override
	public List<String> getUniquePlanStatus() {
		List<String> uniquePlanStatus = eligibilityRepo.getUniquePlanStatus();
		return uniquePlanStatus;
	}

	
	@Override
	public List<SearchResponse> search(SearchRequest request) {
		log.info("Search Criteria {} , {}" , request.getPlanName(),request.getPlanStatus());	
		List<SearchResponse> searchResult = eligibilityRepo.search(request.getPlanName(), request.getPlanStatus());
		log.info(" Search result {} ", searchResult );
		
		return searchResult;
	}

	@Override
	public void generateXLS(HttpServletResponse response) {

	}

	@Override
	public void geneartePDF(HttpServletResponse response) {

	}

}
