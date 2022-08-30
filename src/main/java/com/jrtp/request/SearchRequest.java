package com.jrtp.request;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest implements Serializable {
 	private static final long serialVersionUID = 1L;
//user selected data ==> user may search any of 4 fields data(dynamic)
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
}
