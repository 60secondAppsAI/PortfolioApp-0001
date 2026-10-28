package com.PortfolioApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePortfolioBalanceDTO {

	private Integer ownerId;

	private Integer portfolioBalanceId;

	private Integer portfolioBalanceStatus;
	
	private Integer nextOrPrevious;
}

