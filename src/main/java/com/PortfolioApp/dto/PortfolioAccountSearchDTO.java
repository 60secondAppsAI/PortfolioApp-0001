package com.PortfolioApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PortfolioAccountSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer portfolioAccountId;
	
	private Integer portfolioId;
	
	private String accountNumber;
	
	private String accountType;
	
	private String status;
	
}
