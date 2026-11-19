package com.PortfolioApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PortfolioApp.domain.PortfolioAccount;
import com.PortfolioApp.dto.PortfolioAccountDTO;
import com.PortfolioApp.dto.PortfolioAccountSearchDTO;
import com.PortfolioApp.dto.PortfolioAccountPageDTO;
import com.PortfolioApp.dto.PortfolioAccountConvertCriteriaDTO;
import com.PortfolioApp.service.GenericService;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PortfolioAccountService extends GenericService<PortfolioAccount, Integer> {

	List<PortfolioAccount> findAll();

	ResultDTO addPortfolioAccount(PortfolioAccountDTO portfolioAccountDTO, RequestDTO requestDTO);

	ResultDTO updatePortfolioAccount(PortfolioAccountDTO portfolioAccountDTO, RequestDTO requestDTO);

    Page<PortfolioAccount> getAllPortfolioAccounts(Pageable pageable);

    Page<PortfolioAccount> getAllPortfolioAccounts(Specification<PortfolioAccount> spec, Pageable pageable);

	ResponseEntity<PortfolioAccountPageDTO> getPortfolioAccounts(PortfolioAccountSearchDTO portfolioAccountSearchDTO);
	
	List<PortfolioAccountDTO> convertPortfolioAccountsToPortfolioAccountDTOs(List<PortfolioAccount> portfolioAccounts, PortfolioAccountConvertCriteriaDTO convertCriteria);

	PortfolioAccountDTO getPortfolioAccountDTOById(Integer portfolioAccountId);



	
}
