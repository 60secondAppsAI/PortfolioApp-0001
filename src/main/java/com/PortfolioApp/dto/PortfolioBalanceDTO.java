package com.PortfolioApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PortfolioBalanceDTO {

	private Integer portfolioBalanceId;

	private Integer portfolioId;

	private Double cashBalance;

	private Double marketValue;

	private Double totalValue;

	private Date calculatedAt;






}
