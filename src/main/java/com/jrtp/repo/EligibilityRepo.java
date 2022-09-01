package com.jrtp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrtp.entity.EligibilityDetails;

import com.jrtp.response.SearchResponse;

public interface EligibilityRepo extends JpaRepository<EligibilityDetails, Integer> {

	@Query(" SELECT DISTINCT plan.planName FROM EligibilityDetails plan ")
	public List<String> getUniquePlanNames();

	@Query(" SELECT DISTINCT plan.planStatus FROM EligibilityDetails plan ")
	public List<String> getUniquePlanStatus();

//	@Query( " SELECT plan.name , plan.mobileNum, plan.email,"
//			+ " plan.geneder , plan.ssn , plan.planName , plan.planStatus "
//			+ " FROM EligibilityDetails plan "
//			+ " WHERE plan.name = :name AND plan.planStatus = :status ")

	@Query("SELECT new com.jrtp.response.SearchResponse (plan.name , plan.mobileNum, plan.email, "
			+ " plan.geneder , plan.ssn , plan.planName , plan.planStatus ) " 
			+ " FROM EligibilityDetails plan  "
			+ " WHERE plan.planName = :name AND plan.planStatus = :status ")
	public List<SearchResponse> search(@Param("name") String planName, @Param("status") String planStatus);

	
}
