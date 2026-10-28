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
import com.PortfolioApp.dao.PortfolioStrategyDAO;
import com.PortfolioApp.domain.PortfolioStrategy;
import com.PortfolioApp.dto.PortfolioStrategyDTO;
import com.PortfolioApp.dto.PortfolioStrategySearchDTO;
import com.PortfolioApp.dto.PortfolioStrategyPageDTO;
import com.PortfolioApp.dto.PortfolioStrategyConvertCriteriaDTO;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import com.PortfolioApp.service.PortfolioStrategyService;
import com.PortfolioApp.util.ControllerUtils;


@Service
public class PortfolioStrategyServiceImpl extends GenericServiceImpl<PortfolioStrategy, Integer> implements PortfolioStrategyService {

    private final static Logger logger = LoggerFactory.getLogger(PortfolioStrategyServiceImpl.class);

	@Autowired
	PortfolioStrategyDAO portfolioStrategyDao;

	

	@Override
	public GenericDAO<PortfolioStrategy, Integer> getDAO() {
		return (GenericDAO<PortfolioStrategy, Integer>) portfolioStrategyDao;
	}
	
	public List<PortfolioStrategy> findAll () {
		List<PortfolioStrategy> portfolioStrategys = portfolioStrategyDao.findAll();
		
		return portfolioStrategys;	
		
	}

	public ResultDTO addPortfolioStrategy(PortfolioStrategyDTO portfolioStrategyDTO, RequestDTO requestDTO) {

		PortfolioStrategy portfolioStrategy = new PortfolioStrategy();

		portfolioStrategy.setPortfolioStrategyId(portfolioStrategyDTO.getPortfolioStrategyId());

		portfolioStrategy.setPortfolioId(portfolioStrategyDTO.getPortfolioId());

		portfolioStrategy.setStrategyId(portfolioStrategyDTO.getStrategyId());

		portfolioStrategy.setAllocationPercent(portfolioStrategyDTO.getAllocationPercent());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		portfolioStrategy = portfolioStrategyDao.save(portfolioStrategy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PortfolioStrategy> getAllPortfolioStrategys(Pageable pageable) {
		return portfolioStrategyDao.findAll(pageable);
	}

	public Page<PortfolioStrategy> getAllPortfolioStrategys(Specification<PortfolioStrategy> spec, Pageable pageable) {
		return portfolioStrategyDao.findAll(spec, pageable);
	}

	public ResponseEntity<PortfolioStrategyPageDTO> getPortfolioStrategys(PortfolioStrategySearchDTO portfolioStrategySearchDTO) {
	
			Integer portfolioStrategyId = portfolioStrategySearchDTO.getPortfolioStrategyId(); 
 			Integer portfolioId = portfolioStrategySearchDTO.getPortfolioId(); 
 			Integer strategyId = portfolioStrategySearchDTO.getStrategyId(); 
  			String sortBy = portfolioStrategySearchDTO.getSortBy();
			String sortOrder = portfolioStrategySearchDTO.getSortOrder();
			String searchQuery = portfolioStrategySearchDTO.getSearchQuery();
			Integer page = portfolioStrategySearchDTO.getPage();
			Integer size = portfolioStrategySearchDTO.getSize();

	        Specification<PortfolioStrategy> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, portfolioStrategyId, "portfolioStrategyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, strategyId, "strategyId"); 
			
			

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

		Page<PortfolioStrategy> portfolioStrategys = this.getAllPortfolioStrategys(spec, pageable);
		
		//System.out.println(String.valueOf(portfolioStrategys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(portfolioStrategys.getTotalPages()));
		
		List<PortfolioStrategy> portfolioStrategysList = portfolioStrategys.getContent();
		
		PortfolioStrategyConvertCriteriaDTO convertCriteria = new PortfolioStrategyConvertCriteriaDTO();
		List<PortfolioStrategyDTO> portfolioStrategyDTOs = this.convertPortfolioStrategysToPortfolioStrategyDTOs(portfolioStrategysList,convertCriteria);
		
		PortfolioStrategyPageDTO portfolioStrategyPageDTO = new PortfolioStrategyPageDTO();
		portfolioStrategyPageDTO.setPortfolioStrategys(portfolioStrategyDTOs);
		portfolioStrategyPageDTO.setTotalElements(portfolioStrategys.getTotalElements());
		return ResponseEntity.ok(portfolioStrategyPageDTO);
	}

	public List<PortfolioStrategyDTO> convertPortfolioStrategysToPortfolioStrategyDTOs(List<PortfolioStrategy> portfolioStrategys, PortfolioStrategyConvertCriteriaDTO convertCriteria) {
		
		List<PortfolioStrategyDTO> portfolioStrategyDTOs = new ArrayList<PortfolioStrategyDTO>();
		
		for (PortfolioStrategy portfolioStrategy : portfolioStrategys) {
			portfolioStrategyDTOs.add(convertPortfolioStrategyToPortfolioStrategyDTO(portfolioStrategy,convertCriteria));
		}
		
		return portfolioStrategyDTOs;

	}
	
	public PortfolioStrategyDTO convertPortfolioStrategyToPortfolioStrategyDTO(PortfolioStrategy portfolioStrategy, PortfolioStrategyConvertCriteriaDTO convertCriteria) {
		
		PortfolioStrategyDTO portfolioStrategyDTO = new PortfolioStrategyDTO();

		portfolioStrategyDTO.setPortfolioStrategyId(portfolioStrategy.getPortfolioStrategyId());

		portfolioStrategyDTO.setPortfolioId(portfolioStrategy.getPortfolioId());

		portfolioStrategyDTO.setStrategyId(portfolioStrategy.getStrategyId());

		portfolioStrategyDTO.setAllocationPercent(portfolioStrategy.getAllocationPercent());
		
		return portfolioStrategyDTO;
	}

	public ResultDTO updatePortfolioStrategy(PortfolioStrategyDTO portfolioStrategyDTO, RequestDTO requestDTO) {
		
		PortfolioStrategy portfolioStrategy = portfolioStrategyDao.getById(portfolioStrategyDTO.getPortfolioStrategyId());
		
		portfolioStrategy.setPortfolioStrategyId(ControllerUtils.setValue(portfolioStrategy.getPortfolioStrategyId(), portfolioStrategyDTO.getPortfolioStrategyId()));
		
		portfolioStrategy.setPortfolioId(ControllerUtils.setValue(portfolioStrategy.getPortfolioId(), portfolioStrategyDTO.getPortfolioId()));
		
		portfolioStrategy.setStrategyId(ControllerUtils.setValue(portfolioStrategy.getStrategyId(), portfolioStrategyDTO.getStrategyId()));
		
		portfolioStrategy.setAllocationPercent(ControllerUtils.setValue(portfolioStrategy.getAllocationPercent(), portfolioStrategyDTO.getAllocationPercent()));

        portfolioStrategy = portfolioStrategyDao.save(portfolioStrategy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PortfolioStrategyDTO getPortfolioStrategyDTOById(Integer portfolioStrategyId) {
	
		PortfolioStrategy portfolioStrategy = portfolioStrategyDao.getById(portfolioStrategyId);
		
		PortfolioStrategyConvertCriteriaDTO convertCriteria = new PortfolioStrategyConvertCriteriaDTO();
		return(this.convertPortfolioStrategyToPortfolioStrategyDTO(portfolioStrategy,convertCriteria));
	}

}
