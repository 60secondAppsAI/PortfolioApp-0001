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
import com.PortfolioApp.dao.PortfolioDAO;
import com.PortfolioApp.domain.Portfolio;
import com.PortfolioApp.dto.PortfolioDTO;
import com.PortfolioApp.dto.PortfolioSearchDTO;
import com.PortfolioApp.dto.PortfolioPageDTO;
import com.PortfolioApp.dto.PortfolioConvertCriteriaDTO;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;
import com.PortfolioApp.service.PortfolioService;
import com.PortfolioApp.util.ControllerUtils;


@Service
public class PortfolioServiceImpl extends GenericServiceImpl<Portfolio, Integer> implements PortfolioService {

    private final static Logger logger = LoggerFactory.getLogger(PortfolioServiceImpl.class);

	@Autowired
	PortfolioDAO portfolioDao;

	

	@Override
	public GenericDAO<Portfolio, Integer> getDAO() {
		return (GenericDAO<Portfolio, Integer>) portfolioDao;
	}
	
	public List<Portfolio> findAll () {
		List<Portfolio> portfolios = portfolioDao.findAll();
		
		return portfolios;	
		
	}

	public ResultDTO addPortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO) {

		Portfolio portfolio = new Portfolio();

		portfolio.setPortfolioId(portfolioDTO.getPortfolioId());

		portfolio.setName(portfolioDTO.getName());

		portfolio.setDescription(portfolioDTO.getDescription());

		portfolio.setBaseCurrency(portfolioDTO.getBaseCurrency());

		portfolio.setStatus(portfolioDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		portfolio = portfolioDao.save(portfolio);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Portfolio> getAllPortfolios(Pageable pageable) {
		return portfolioDao.findAll(pageable);
	}

	public Page<Portfolio> getAllPortfolios(Specification<Portfolio> spec, Pageable pageable) {
		return portfolioDao.findAll(spec, pageable);
	}

	public ResponseEntity<PortfolioPageDTO> getPortfolios(PortfolioSearchDTO portfolioSearchDTO) {
	
			Integer portfolioId = portfolioSearchDTO.getPortfolioId(); 
 			String name = portfolioSearchDTO.getName(); 
 			String description = portfolioSearchDTO.getDescription(); 
 			String baseCurrency = portfolioSearchDTO.getBaseCurrency(); 
 			String status = portfolioSearchDTO.getStatus(); 
 			String sortBy = portfolioSearchDTO.getSortBy();
			String sortOrder = portfolioSearchDTO.getSortOrder();
			String searchQuery = portfolioSearchDTO.getSearchQuery();
			Integer page = portfolioSearchDTO.getPage();
			Integer size = portfolioSearchDTO.getSize();

	        Specification<Portfolio> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, baseCurrency, "baseCurrency"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("baseCurrency")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Portfolio> portfolios = this.getAllPortfolios(spec, pageable);
		
		//System.out.println(String.valueOf(portfolios.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(portfolios.getTotalPages()));
		
		List<Portfolio> portfoliosList = portfolios.getContent();
		
		PortfolioConvertCriteriaDTO convertCriteria = new PortfolioConvertCriteriaDTO();
		List<PortfolioDTO> portfolioDTOs = this.convertPortfoliosToPortfolioDTOs(portfoliosList,convertCriteria);
		
		PortfolioPageDTO portfolioPageDTO = new PortfolioPageDTO();
		portfolioPageDTO.setPortfolios(portfolioDTOs);
		portfolioPageDTO.setTotalElements(portfolios.getTotalElements());
		return ResponseEntity.ok(portfolioPageDTO);
	}

	public List<PortfolioDTO> convertPortfoliosToPortfolioDTOs(List<Portfolio> portfolios, PortfolioConvertCriteriaDTO convertCriteria) {
		
		List<PortfolioDTO> portfolioDTOs = new ArrayList<PortfolioDTO>();
		
		for (Portfolio portfolio : portfolios) {
			portfolioDTOs.add(convertPortfolioToPortfolioDTO(portfolio,convertCriteria));
		}
		
		return portfolioDTOs;

	}
	
	public PortfolioDTO convertPortfolioToPortfolioDTO(Portfolio portfolio, PortfolioConvertCriteriaDTO convertCriteria) {
		
		PortfolioDTO portfolioDTO = new PortfolioDTO();

		portfolioDTO.setPortfolioId(portfolio.getPortfolioId());

		portfolioDTO.setName(portfolio.getName());

		portfolioDTO.setDescription(portfolio.getDescription());

		portfolioDTO.setBaseCurrency(portfolio.getBaseCurrency());

		portfolioDTO.setStatus(portfolio.getStatus());
		
		return portfolioDTO;
	}

	public ResultDTO updatePortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO) {
		
		Portfolio portfolio = portfolioDao.getById(portfolioDTO.getPortfolioId());
		
		portfolio.setPortfolioId(ControllerUtils.setValue(portfolio.getPortfolioId(), portfolioDTO.getPortfolioId()));
		
		portfolio.setName(ControllerUtils.setValue(portfolio.getName(), portfolioDTO.getName()));
		
		portfolio.setDescription(ControllerUtils.setValue(portfolio.getDescription(), portfolioDTO.getDescription()));
		
		portfolio.setBaseCurrency(ControllerUtils.setValue(portfolio.getBaseCurrency(), portfolioDTO.getBaseCurrency()));
		
		portfolio.setStatus(ControllerUtils.setValue(portfolio.getStatus(), portfolioDTO.getStatus()));

        portfolio = portfolioDao.save(portfolio);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PortfolioDTO getPortfolioDTOById(Integer portfolioId) {
	
		Portfolio portfolio = portfolioDao.getById(portfolioId);
		
		PortfolioConvertCriteriaDTO convertCriteria = new PortfolioConvertCriteriaDTO();
		return(this.convertPortfolioToPortfolioDTO(portfolio,convertCriteria));
	}

}
