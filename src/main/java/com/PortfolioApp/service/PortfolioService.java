package com.PortfolioApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PortfolioApp.domain.Portfolio;
import com.PortfolioApp.dto.PortfolioDTO;
import com.PortfolioApp.dto.PortfolioSearchDTO;
import com.PortfolioApp.dto.PortfolioPageDTO;
import com.PortfolioApp.dto.PortfolioConvertCriteriaDTO;
import com.PortfolioApp.service.GenericService;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PortfolioService extends GenericService<Portfolio, Integer> {

	List<Portfolio> findAll();

	ResultDTO addPortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO);

	ResultDTO updatePortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO);

    Page<Portfolio> getAllPortfolios(Pageable pageable);

    Page<Portfolio> getAllPortfolios(Specification<Portfolio> spec, Pageable pageable);

	ResponseEntity<PortfolioPageDTO> getPortfolios(PortfolioSearchDTO portfolioSearchDTO);
	
	List<PortfolioDTO> convertPortfoliosToPortfolioDTOs(List<Portfolio> portfolios, PortfolioConvertCriteriaDTO convertCriteria);

	PortfolioDTO getPortfolioDTOById(Integer portfolioId);



	
}
