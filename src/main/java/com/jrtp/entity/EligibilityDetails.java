package com.jrtp.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Eligibility_Dtl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EligibilityDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ELIG_ID")
	private Integer eligId;

	
	@Column(name = "NAME")
	private String name;

	@Column(name = "MOBILE_NUM")
	private Long mobileNum;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "gender")
	private Character geneder;

	@Column(name="SSN_NUM")
    private Long ssn;
	
	@Column(name="PLAN_NAME")
	private String planName;
	
	@Column(name="PLAN_STATUS")
	private String planStatus;
	
	@Column(name="START_DT")
	@CreationTimestamp
	private LocalDate startDate;
	
	@Column(name="END_DT")
	@CreationTimestamp
	private LocalDate endDate;
	
	@Column(name="CREATION_DT",updatable = false)
	@CreationTimestamp
	private LocalDate creationDate;
	
	@Column(name="UPDATED_DT",insertable = false)
	@CreationTimestamp
	private LocalDate updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	
	
}
