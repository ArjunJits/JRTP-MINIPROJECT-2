package com.jrtp.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
		return eligibilityRepo.getUniquePlanNames();
	}

	@Override
	public List<String> getUniquePlanStatus() {
		return eligibilityRepo.getUniquePlanStatus();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		log.info("Search Criteria {} , {}", request.getPlanName(), request.getPlanStatus());
		List<SearchResponse> searchResult = eligibilityRepo.search(request.getPlanName(), request.getPlanStatus());
		log.info(" Search result {} ", searchResult);

		return searchResult;
	}

	@Override // VIMP dynamic query concept (Query by example) we can also use criteriaBuilder
	public List<SearchResponse> searchRecords(SearchRequest request) {
		List<SearchResponse> response = new ArrayList<>();

		EligibilityDetails queryBulider = new EligibilityDetails();

		String planName = request.getPlanName();
		if (planName != null && !planName.equals("")) {
			queryBulider.setPlanName(planName);
		}

		String planStatus = request.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			queryBulider.setPlanStatus(planStatus);
		}

		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			queryBulider.setStartDate(planStartDate);
		}

		LocalDate planEndDate = request.getPlanEndDate();
		if (planEndDate != null) {
			queryBulider.setEndDate(planEndDate);
		}

		Example<EligibilityDetails> example = Example.of(queryBulider);

		List<EligibilityDetails> entities = eligibilityRepo.findAll(example);

		for (EligibilityDetails entity : entities) {
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr); // entity to DTO copy variables must much
			response.add(sr);
		}

		return response;
	}

	@Override
	public void generateXLS(HttpServletResponse response) {
		List<EligibilityDetails> entities = eligibilityRepo.findAll();
		// Notes: excel is called as workbook
		// Inside the workbook we have sheet1 sheet2 sheet3 ...
		// sheet will have rows and columns ==> workbook-->sheet-->row --> cells(data)

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Eligibility_details");
		HSSFRow headerRow = sheet.createRow(0);
		//Header
		headerRow.createCell(0).setCellValue("Sno");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");
   
		// forEach entity data row
		int i =1;
		for (EligibilityDetails entity:entities)
		{
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(i);
			dataRow.createCell(1).setCellValue(entity.getName());
			dataRow.createCell(2).setCellValue(entity.getMobileNum());
			dataRow.createCell(3).setCellValue(entity.getGeneder().toString());
			dataRow.createCell(4).setCellValue(entity.getSsn());
			i++;
		}	
		
		
		try {
			FileOutputStream output = new FileOutputStream(new File("Eligibility.xls"));
			workbook.write(output);
			
			output.close();
			log.info(" Xls Work book creatiion done {} ",workbook);
			
		} catch (FileNotFoundException e) {
			log.info("file not found exp" +e.getMessage());
		}catch(Exception e) {
			log.info("Exception occured" +e.getMessage());
		}
		
	}


	@Override
	public void geneartePDF(HttpServletResponse response) {

	}

}
