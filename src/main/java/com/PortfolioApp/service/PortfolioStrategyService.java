package com.PortfolioApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PortfolioApp.domain.PortfolioStrategy;
import com.PortfolioApp.dto.PortfolioStrategyDTO;
import com.PortfolioApp.dto.PortfolioStrategySearchDTO;
import com.PortfolioApp.dto.PortfolioStrategyPageDTO;
import com.PortfolioApp.dto.PortfolioStrategyConvertCriteriaDTO;
import com.PortfolioApp.service.GenericService;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PortfolioStrategyService extends GenericService<PortfolioStrategy, Integer> {

	List<PortfolioStrategy> findAll();

	ResultDTO addPortfolioStrategy(PortfolioStrategyDTO portfolioStrategyDTO, RequestDTO requestDTO);

	ResultDTO updatePortfolioStrategy(PortfolioStrategyDTO portfolioStrategyDTO, RequestDTO requestDTO);

    Page<PortfolioStrategy> getAllPortfolioStrategys(Pageable pageable);

    Page<PortfolioStrategy> getAllPortfolioStrategys(Specification<PortfolioStrategy> spec, Pageable pageable);

	ResponseEntity<PortfolioStrategyPageDTO> getPortfolioStrategys(PortfolioStrategySearchDTO portfolioStrategySearchDTO);
	
	List<PortfolioStrategyDTO> convertPortfolioStrategysToPortfolioStrategyDTOs(List<PortfolioStrategy> portfolioStrategys, PortfolioStrategyConvertCriteriaDTO convertCriteria);

	PortfolioStrategyDTO getPortfolioStrategyDTOById(Integer portfolioStrategyId);



	
}
