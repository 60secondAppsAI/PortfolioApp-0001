package com.PortfolioApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PortfolioAccountDTO {

	private Integer portfolioAccountId;

	private Integer portfolioId;

	private String accountNumber;

	private String accountType;

	private String status;






}
