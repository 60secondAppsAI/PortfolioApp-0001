package com.PortfolioApp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PortfolioBalancePageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<PortfolioBalanceDTO> portfolioBalances;
}





