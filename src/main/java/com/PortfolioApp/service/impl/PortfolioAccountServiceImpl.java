package com.PortfolioApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.PortfolioApp.dao.GenericDAO;
import com.PortfolioApp.service.GenericService;
import com.PortfolioApp.service.impl.GenericServiceImpl;
import com.PortfolioApp.dao.PortfolioAccountDAO;
import com.PortfolioApp.domain.PortfolioAccount;
import com.PortfolioApp.dto.PortfolioAccountDTO;
import com.PortfolioApp.dto.PortfolioAccountSearchDTO;
import com.PortfolioApp.dto.PortfolioAccountPageDTO;
import com.PortfolioApp.dto.PortfolioAccountConvertCriteriaDTO;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import com.PortfolioApp.service.PortfolioAccountService;
import com.PortfolioApp.util.ControllerUtils;


@Service
public class PortfolioAccountServiceImpl extends GenericServiceImpl<PortfolioAccount, Integer> implements PortfolioAccountService {

    private final static Logger logger = LoggerFactory.getLogger(PortfolioAccountServiceImpl.class);

	@Autowired
	PortfolioAccountDAO portfolioAccountDao;

	

	@Override
	public GenericDAO<PortfolioAccount, Integer> getDAO() {
		return (GenericDAO<PortfolioAccount, Integer>) portfolioAccountDao;
	}
	
	public List<PortfolioAccount> findAll () {
		List<PortfolioAccount> portfolioAccounts = portfolioAccountDao.findAll();
		
		return portfolioAccounts;	
		
	}

	public ResultDTO addPortfolioAccount(PortfolioAccountDTO portfolioAccountDTO, RequestDTO requestDTO) {

		PortfolioAccount portfolioAccount = new PortfolioAccount();

		portfolioAccount.setPortfolioAccountId(portfolioAccountDTO.getPortfolioAccountId());

		portfolioAccount.setPortfolioId(portfolioAccountDTO.getPortfolioId());

		portfolioAccount.setAccountNumber(portfolioAccountDTO.getAccountNumber());

		portfolioAccount.setAccountType(portfolioAccountDTO.getAccountType());

		portfolioAccount.setStatus(portfolioAccountDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		portfolioAccount = portfolioAccountDao.save(portfolioAccount);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PortfolioAccount> getAllPortfolioAccounts(Pageable pageable) {
		return portfolioAccountDao.findAll(pageable);
	}

	public Page<PortfolioAccount> getAllPortfolioAccounts(Specification<PortfolioAccount> spec, Pageable pageable) {
		return portfolioAccountDao.findAll(spec, pageable);
	}

	public ResponseEntity<PortfolioAccountPageDTO> getPortfolioAccounts(PortfolioAccountSearchDTO portfolioAccountSearchDTO) {
	
			Integer portfolioAccountId = portfolioAccountSearchDTO.getPortfolioAccountId(); 
 			Integer portfolioId = portfolioAccountSearchDTO.getPortfolioId(); 
 			String accountNumber = portfolioAccountSearchDTO.getAccountNumber(); 
 			String accountType = portfolioAccountSearchDTO.getAccountType(); 
 			String status = portfolioAccountSearchDTO.getStatus(); 
 			String sortBy = portfolioAccountSearchDTO.getSortBy();
			String sortOrder = portfolioAccountSearchDTO.getSortOrder();
			String searchQuery = portfolioAccountSearchDTO.getSearchQuery();
			Integer page = portfolioAccountSearchDTO.getPage();
			Integer size = portfolioAccountSearchDTO.getSize();

	        Specification<PortfolioAccount> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, portfolioAccountId, "portfolioAccountId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, accountNumber, "accountNumber"); 
			
			spec = ControllerUtils.andIfNecessary(spec, accountType, "accountType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("accountNumber")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("accountType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<PortfolioAccount> portfolioAccounts = this.getAllPortfolioAccounts(spec, pageable);
		
		//System.out.println(String.valueOf(portfolioAccounts.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(portfolioAccounts.getTotalPages()));
		
		List<PortfolioAccount> portfolioAccountsList = portfolioAccounts.getContent();
		
		PortfolioAccountConvertCriteriaDTO convertCriteria = new PortfolioAccountConvertCriteriaDTO();
		List<PortfolioAccountDTO> portfolioAccountDTOs = this.convertPortfolioAccountsToPortfolioAccountDTOs(portfolioAccountsList,convertCriteria);
		
		PortfolioAccountPageDTO portfolioAccountPageDTO = new PortfolioAccountPageDTO();
		portfolioAccountPageDTO.setPortfolioAccounts(portfolioAccountDTOs);
		portfolioAccountPageDTO.setTotalElements(portfolioAccounts.getTotalElements());
		return ResponseEntity.ok(portfolioAccountPageDTO);
	}

	public List<PortfolioAccountDTO> convertPortfolioAccountsToPortfolioAccountDTOs(List<PortfolioAccount> portfolioAccounts, PortfolioAccountConvertCriteriaDTO convertCriteria) {
		
		List<PortfolioAccountDTO> portfolioAccountDTOs = new ArrayList<PortfolioAccountDTO>();
		
		for (PortfolioAccount portfolioAccount : portfolioAccounts) {
			portfolioAccountDTOs.add(convertPortfolioAccountToPortfolioAccountDTO(portfolioAccount,convertCriteria));
		}
		
		return portfolioAccountDTOs;

	}
	
	public PortfolioAccountDTO convertPortfolioAccountToPortfolioAccountDTO(PortfolioAccount portfolioAccount, PortfolioAccountConvertCriteriaDTO convertCriteria) {
		
		PortfolioAccountDTO portfolioAccountDTO = new PortfolioAccountDTO();

		portfolioAccountDTO.setPortfolioAccountId(portfolioAccount.getPortfolioAccountId());

		portfolioAccountDTO.setPortfolioId(portfolioAccount.getPortfolioId());

		portfolioAccountDTO.setAccountNumber(portfolioAccount.getAccountNumber());

		portfolioAccountDTO.setAccountType(portfolioAccount.getAccountType());

		portfolioAccountDTO.setStatus(portfolioAccount.getStatus());
		
		return portfolioAccountDTO;
	}

	public ResultDTO updatePortfolioAccount(PortfolioAccountDTO portfolioAccountDTO, RequestDTO requestDTO) {
		
		PortfolioAccount portfolioAccount = portfolioAccountDao.getById(portfolioAccountDTO.getPortfolioAccountId());
		
		portfolioAccount.setPortfolioAccountId(ControllerUtils.setValue(portfolioAccount.getPortfolioAccountId(), portfolioAccountDTO.getPortfolioAccountId()));
		
		portfolioAccount.setPortfolioId(ControllerUtils.setValue(portfolioAccount.getPortfolioId(), portfolioAccountDTO.getPortfolioId()));
		
		portfolioAccount.setAccountNumber(ControllerUtils.setValue(portfolioAccount.getAccountNumber(), portfolioAccountDTO.getAccountNumber()));
		
		portfolioAccount.setAccountType(ControllerUtils.setValue(portfolioAccount.getAccountType(), portfolioAccountDTO.getAccountType()));
		
		portfolioAccount.setStatus(ControllerUtils.setValue(portfolioAccount.getStatus(), portfolioAccountDTO.getStatus()));

        portfolioAccount = portfolioAccountDao.save(portfolioAccount);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PortfolioAccountDTO getPortfolioAccountDTOById(Integer portfolioAccountId) {
	
		PortfolioAccount portfolioAccount = portfolioAccountDao.getById(portfolioAccountId);
		
		PortfolioAccountConvertCriteriaDTO convertCriteria = new PortfolioAccountConvertCriteriaDTO();
		return(this.convertPortfolioAccountToPortfolioAccountDTO(portfolioAccount,convertCriteria));
	}

}
