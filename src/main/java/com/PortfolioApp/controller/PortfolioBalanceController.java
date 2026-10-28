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

import com.PortfolioApp.domain.PortfolioBalance;
import com.PortfolioApp.dto.PortfolioBalanceDTO;
import com.PortfolioApp.dto.PortfolioBalanceSearchDTO;
import com.PortfolioApp.dto.PortfolioBalancePageDTO;
import com.PortfolioApp.service.PortfolioBalanceService;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/portfolioBalance")
@RestController
public class PortfolioBalanceController {

	private final static Logger logger = LoggerFactory.getLogger(PortfolioBalanceController.class);

	@Autowired
	PortfolioBalanceService portfolioBalanceService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PortfolioBalance> getAll() {

		List<PortfolioBalance> portfolioBalances = portfolioBalanceService.findAll();
		
		return portfolioBalances;	
	}

	@GetMapping(value = "/{portfolioBalanceId}")
	@ResponseBody
	public PortfolioBalanceDTO getPortfolioBalance(@PathVariable Integer portfolioBalanceId) {
		
		return (portfolioBalanceService.getPortfolioBalanceDTOById(portfolioBalanceId));
	}

 	@RequestMapping(value = "/addPortfolioBalance", method = RequestMethod.POST)
	public ResponseEntity<?> addPortfolioBalance(@RequestBody PortfolioBalanceDTO portfolioBalanceDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioBalanceService.addPortfolioBalance(portfolioBalanceDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/portfolioBalances")
	public ResponseEntity<PortfolioBalancePageDTO> getPortfolioBalances(PortfolioBalanceSearchDTO portfolioBalanceSearchDTO) {
 
		return portfolioBalanceService.getPortfolioBalances(portfolioBalanceSearchDTO);
	}	

	@RequestMapping(value = "/updatePortfolioBalance", method = RequestMethod.POST)
	public ResponseEntity<?> updatePortfolioBalance(@RequestBody PortfolioBalanceDTO portfolioBalanceDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioBalanceService.updatePortfolioBalance(portfolioBalanceDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
