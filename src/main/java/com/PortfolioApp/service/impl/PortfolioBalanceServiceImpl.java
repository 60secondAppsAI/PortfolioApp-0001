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
import com.PortfolioApp.dao.PortfolioBalanceDAO;
import com.PortfolioApp.domain.PortfolioBalance;
import com.PortfolioApp.dto.PortfolioBalanceDTO;
import com.PortfolioApp.dto.PortfolioBalanceSearchDTO;
import com.PortfolioApp.dto.PortfolioBalancePageDTO;
import com.PortfolioApp.dto.PortfolioBalanceConvertCriteriaDTO;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import com.PortfolioApp.service.PortfolioBalanceService;
import com.PortfolioApp.util.ControllerUtils;


@Service
public class PortfolioBalanceServiceImpl extends GenericServiceImpl<PortfolioBalance, Integer> implements PortfolioBalanceService {

    private final static Logger logger = LoggerFactory.getLogger(PortfolioBalanceServiceImpl.class);

	@Autowired
	PortfolioBalanceDAO portfolioBalanceDao;

	

	@Override
	public GenericDAO<PortfolioBalance, Integer> getDAO() {
		return (GenericDAO<PortfolioBalance, Integer>) portfolioBalanceDao;
	}
	
	public List<PortfolioBalance> findAll () {
		List<PortfolioBalance> portfolioBalances = portfolioBalanceDao.findAll();
		
		return portfolioBalances;	
		
	}

	public ResultDTO addPortfolioBalance(PortfolioBalanceDTO portfolioBalanceDTO, RequestDTO requestDTO) {

		PortfolioBalance portfolioBalance = new PortfolioBalance();

		portfolioBalance.setPortfolioBalanceId(portfolioBalanceDTO.getPortfolioBalanceId());

		portfolioBalance.setPortfolioId(portfolioBalanceDTO.getPortfolioId());

		portfolioBalance.setCashBalance(portfolioBalanceDTO.getCashBalance());

		portfolioBalance.setMarketValue(portfolioBalanceDTO.getMarketValue());

		portfolioBalance.setTotalValue(portfolioBalanceDTO.getTotalValue());

		portfolioBalance.setCalculatedAt(portfolioBalanceDTO.getCalculatedAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		portfolioBalance = portfolioBalanceDao.save(portfolioBalance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PortfolioBalance> getAllPortfolioBalances(Pageable pageable) {
		return portfolioBalanceDao.findAll(pageable);
	}

	public Page<PortfolioBalance> getAllPortfolioBalances(Specification<PortfolioBalance> spec, Pageable pageable) {
		return portfolioBalanceDao.findAll(spec, pageable);
	}

	public ResponseEntity<PortfolioBalancePageDTO> getPortfolioBalances(PortfolioBalanceSearchDTO portfolioBalanceSearchDTO) {
	
			Integer portfolioBalanceId = portfolioBalanceSearchDTO.getPortfolioBalanceId(); 
 			Integer portfolioId = portfolioBalanceSearchDTO.getPortfolioId(); 
    			Date calculatedAt = portfolioBalanceSearchDTO.getCalculatedAt();
 			String sortBy = portfolioBalanceSearchDTO.getSortBy();
			String sortOrder = portfolioBalanceSearchDTO.getSortOrder();
			String searchQuery = portfolioBalanceSearchDTO.getSearchQuery();
			Integer page = portfolioBalanceSearchDTO.getPage();
			Integer size = portfolioBalanceSearchDTO.getSize();

	        Specification<PortfolioBalance> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, portfolioBalanceId, "portfolioBalanceId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			
			
			
			spec = ControllerUtils.andIfNecessary(spec, calculatedAt, "calculatedAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<PortfolioBalance> portfolioBalances = this.getAllPortfolioBalances(spec, pageable);
		
		//System.out.println(String.valueOf(portfolioBalances.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(portfolioBalances.getTotalPages()));
		
		List<PortfolioBalance> portfolioBalancesList = portfolioBalances.getContent();
		
		PortfolioBalanceConvertCriteriaDTO convertCriteria = new PortfolioBalanceConvertCriteriaDTO();
		List<PortfolioBalanceDTO> portfolioBalanceDTOs = this.convertPortfolioBalancesToPortfolioBalanceDTOs(portfolioBalancesList,convertCriteria);
		
		PortfolioBalancePageDTO portfolioBalancePageDTO = new PortfolioBalancePageDTO();
		portfolioBalancePageDTO.setPortfolioBalances(portfolioBalanceDTOs);
		portfolioBalancePageDTO.setTotalElements(portfolioBalances.getTotalElements());
		return ResponseEntity.ok(portfolioBalancePageDTO);
	}

	public List<PortfolioBalanceDTO> convertPortfolioBalancesToPortfolioBalanceDTOs(List<PortfolioBalance> portfolioBalances, PortfolioBalanceConvertCriteriaDTO convertCriteria) {
		
		List<PortfolioBalanceDTO> portfolioBalanceDTOs = new ArrayList<PortfolioBalanceDTO>();
		
		for (PortfolioBalance portfolioBalance : portfolioBalances) {
			portfolioBalanceDTOs.add(convertPortfolioBalanceToPortfolioBalanceDTO(portfolioBalance,convertCriteria));
		}
		
		return portfolioBalanceDTOs;

	}
	
	public PortfolioBalanceDTO convertPortfolioBalanceToPortfolioBalanceDTO(PortfolioBalance portfolioBalance, PortfolioBalanceConvertCriteriaDTO convertCriteria) {
		
		PortfolioBalanceDTO portfolioBalanceDTO = new PortfolioBalanceDTO();

		portfolioBalanceDTO.setPortfolioBalanceId(portfolioBalance.getPortfolioBalanceId());

		portfolioBalanceDTO.setPortfolioId(portfolioBalance.getPortfolioId());

		portfolioBalanceDTO.setCashBalance(portfolioBalance.getCashBalance());

		portfolioBalanceDTO.setMarketValue(portfolioBalance.getMarketValue());

		portfolioBalanceDTO.setTotalValue(portfolioBalance.getTotalValue());

		portfolioBalanceDTO.setCalculatedAt(portfolioBalance.getCalculatedAt());
		
		return portfolioBalanceDTO;
	}

	public ResultDTO updatePortfolioBalance(PortfolioBalanceDTO portfolioBalanceDTO, RequestDTO requestDTO) {
		
		PortfolioBalance portfolioBalance = portfolioBalanceDao.getById(portfolioBalanceDTO.getPortfolioBalanceId());
		
		portfolioBalance.setPortfolioBalanceId(ControllerUtils.setValue(portfolioBalance.getPortfolioBalanceId(), portfolioBalanceDTO.getPortfolioBalanceId()));
		
		portfolioBalance.setPortfolioId(ControllerUtils.setValue(portfolioBalance.getPortfolioId(), portfolioBalanceDTO.getPortfolioId()));
		
		portfolioBalance.setCashBalance(ControllerUtils.setValue(portfolioBalance.getCashBalance(), portfolioBalanceDTO.getCashBalance()));
		
		portfolioBalance.setMarketValue(ControllerUtils.setValue(portfolioBalance.getMarketValue(), portfolioBalanceDTO.getMarketValue()));
		
		portfolioBalance.setTotalValue(ControllerUtils.setValue(portfolioBalance.getTotalValue(), portfolioBalanceDTO.getTotalValue()));
		
		portfolioBalance.setCalculatedAt(ControllerUtils.setValue(portfolioBalance.getCalculatedAt(), portfolioBalanceDTO.getCalculatedAt()));

        portfolioBalance = portfolioBalanceDao.save(portfolioBalance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PortfolioBalanceDTO getPortfolioBalanceDTOById(Integer portfolioBalanceId) {
	
		PortfolioBalance portfolioBalance = portfolioBalanceDao.getById(portfolioBalanceId);
		
		PortfolioBalanceConvertCriteriaDTO convertCriteria = new PortfolioBalanceConvertCriteriaDTO();
		return(this.convertPortfolioBalanceToPortfolioBalanceDTO(portfolioBalance,convertCriteria));
	}

}
