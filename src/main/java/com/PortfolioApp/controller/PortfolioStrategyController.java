package com.PortfolioApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.PortfolioApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.PortfolioApp.domain.PortfolioStrategy;
import com.PortfolioApp.dto.PortfolioStrategyDTO;
import com.PortfolioApp.dto.PortfolioStrategySearchDTO;
import com.PortfolioApp.dto.PortfolioStrategyPageDTO;
import com.PortfolioApp.service.PortfolioStrategyService;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/portfolioStrategy")
@RestController
public class PortfolioStrategyController {

	private final static Logger logger = LoggerFactory.getLogger(PortfolioStrategyController.class);

	@Autowired
	PortfolioStrategyService portfolioStrategyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PortfolioStrategy> getAll() {

		List<PortfolioStrategy> portfolioStrategys = portfolioStrategyService.findAll();
		
		return portfolioStrategys;	
	}

	@GetMapping(value = "/{portfolioStrategyId}")
	@ResponseBody
	public PortfolioStrategyDTO getPortfolioStrategy(@PathVariable Integer portfolioStrategyId) {
		
		return (portfolioStrategyService.getPortfolioStrategyDTOById(portfolioStrategyId));
	}

 	@RequestMapping(value = "/addPortfolioStrategy", method = RequestMethod.POST)
	public ResponseEntity<?> addPortfolioStrategy(@RequestBody PortfolioStrategyDTO portfolioStrategyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioStrategyService.addPortfolioStrategy(portfolioStrategyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/portfolioStrategys")
	public ResponseEntity<PortfolioStrategyPageDTO> getPortfolioStrategys(PortfolioStrategySearchDTO portfolioStrategySearchDTO) {
 
		return portfolioStrategyService.getPortfolioStrategys(portfolioStrategySearchDTO);
	}	

	@RequestMapping(value = "/updatePortfolioStrategy", method = RequestMethod.POST)
	public ResponseEntity<?> updatePortfolioStrategy(@RequestBody PortfolioStrategyDTO portfolioStrategyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioStrategyService.updatePortfolioStrategy(portfolioStrategyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
