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

import com.PortfolioApp.domain.PortfolioAccount;
import com.PortfolioApp.dto.PortfolioAccountDTO;
import com.PortfolioApp.dto.PortfolioAccountSearchDTO;
import com.PortfolioApp.dto.PortfolioAccountPageDTO;
import com.PortfolioApp.service.PortfolioAccountService;
import com.PortfolioApp.dto.common.RequestDTO;
import com.PortfolioApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/portfolioAccount")
@RestController
public class PortfolioAccountController {

	private final static Logger logger = LoggerFactory.getLogger(PortfolioAccountController.class);

	@Autowired
	PortfolioAccountService portfolioAccountService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PortfolioAccount> getAll() {

		List<PortfolioAccount> portfolioAccounts = portfolioAccountService.findAll();
		
		return portfolioAccounts;	
	}

	@GetMapping(value = "/{portfolioAccountId}")
	@ResponseBody
	public PortfolioAccountDTO getPortfolioAccount(@PathVariable Integer portfolioAccountId) {
		
		return (portfolioAccountService.getPortfolioAccountDTOById(portfolioAccountId));
	}

 	@RequestMapping(value = "/addPortfolioAccount", method = RequestMethod.POST)
	public ResponseEntity<?> addPortfolioAccount(@RequestBody PortfolioAccountDTO portfolioAccountDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioAccountService.addPortfolioAccount(portfolioAccountDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/portfolioAccounts")
	public ResponseEntity<PortfolioAccountPageDTO> getPortfolioAccounts(PortfolioAccountSearchDTO portfolioAccountSearchDTO) {
 
		return portfolioAccountService.getPortfolioAccounts(portfolioAccountSearchDTO);
	}	

	@RequestMapping(value = "/updatePortfolioAccount", method = RequestMethod.POST)
	public ResponseEntity<?> updatePortfolioAccount(@RequestBody PortfolioAccountDTO portfolioAccountDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioAccountService.updatePortfolioAccount(portfolioAccountDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
