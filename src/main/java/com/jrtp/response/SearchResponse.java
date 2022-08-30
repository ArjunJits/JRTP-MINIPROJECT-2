package com.jrtp.response;




import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	// response data  that is generated in output 
	private String name;
	private Long mobileNum;
	private String email;
	private Character geneder;
    private Long ssn;
	private String planName;
	private String planStatus;
	
	

}
