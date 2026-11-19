package com.PortfolioApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePortfolioStrategyDTO {

	private Integer ownerId;

	private Integer portfolioStrategyId;

	private Integer portfolioStrategyStatus;
	
	private Integer nextOrPrevious;
}

