package com.PortfolioApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePortfolioAccountDTO {

	private Integer ownerId;

	private Integer portfolioAccountId;

	private Integer portfolioAccountStatus;
	
	private Integer nextOrPrevious;
}

