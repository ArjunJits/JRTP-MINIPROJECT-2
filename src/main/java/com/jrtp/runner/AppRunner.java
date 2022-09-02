package com.jrtp.runner;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jrtp.entity.EligibilityDetails;
import com.jrtp.repo.EligibilityRepo;

@Component
public class AppRunner implements ApplicationRunner {
    @Autowired
	private EligibilityRepo eligibilityRepo;
	
    @Override
	public void run(ApplicationArguments args) throws Exception {
		
    	for (int i = 4 ; i < 15; i ++)
    	{
    	EligibilityDetails rec2= EligibilityDetails.builder()
    			                 .eligId(i)
    			                 .name("arjun reddy" + i)
    			                 .mobileNum(99668099L + i)
    			                 .email("mallik@gmail.com" + i)
    			                 .geneder('M')
    			                 .ssn(123456L)
    			                 .planName("Midicare")
    			                 .planStatus("Active")
    			                 .creationDate(LocalDate.parse("2020-08-01").plusDays(i))
    			                 .updatedDate(LocalDate.parse("2020-08-01").plusDays(i))
    			                 .startDate(LocalDate.parse("2020-08-01").plusDays(i))
    			                 .endDate(LocalDate.parse("2020-08-01").plusDays(i))
    			                 .createdBy("arjun" + i)
    			                 .updatedBy("arjun" + i)
    			                 .build();
    	    	eligibilityRepo.save(rec2);
    	    	
    	}
    	
    }

}
