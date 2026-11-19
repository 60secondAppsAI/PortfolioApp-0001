package com.PortfolioApp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PortfolioStrategyPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<PortfolioStrategyDTO> portfolioStrategys;
}





