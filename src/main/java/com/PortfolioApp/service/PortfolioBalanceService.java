package com.PortfolioApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PortfolioApp.domain.PortfolioBalance;
import com.PortfolioApp.dto.PortfolioBalanceDTO;
import com.PortfolioApp.dto.PortfolioBalanceSearchDTO;
import com.PortfolioApp.dto.PortfolioBalancePageDTO;
import com.PortfolioApp.dto.PortfolioBalanceConvertCriteriaDTO;
import com.PortfolioApp.service.GenericService;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PortfolioBalanceService extends GenericService<PortfolioBalance, Integer> {

	List<PortfolioBalance> findAll();

	ResultDTO addPortfolioBalance(PortfolioBalanceDTO portfolioBalanceDTO, RequestDTO requestDTO);

	ResultDTO updatePortfolioBalance(PortfolioBalanceDTO portfolioBalanceDTO, RequestDTO requestDTO);

    Page<PortfolioBalance> getAllPortfolioBalances(Pageable pageable);

    Page<PortfolioBalance> getAllPortfolioBalances(Specification<PortfolioBalance> spec, Pageable pageable);

	ResponseEntity<PortfolioBalancePageDTO> getPortfolioBalances(PortfolioBalanceSearchDTO portfolioBalanceSearchDTO);
	
	List<PortfolioBalanceDTO> convertPortfolioBalancesToPortfolioBalanceDTOs(List<PortfolioBalance> portfolioBalances, PortfolioBalanceConvertCriteriaDTO convertCriteria);

	PortfolioBalanceDTO getPortfolioBalanceDTOById(Integer portfolioBalanceId);



	
}
