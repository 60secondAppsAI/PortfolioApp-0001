package com.PortfolioApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePortfolioDTO {

	private Integer ownerId;

	private Integer portfolioId;

	private Integer portfolioStatus;
	
	private Integer nextOrPrevious;
}

