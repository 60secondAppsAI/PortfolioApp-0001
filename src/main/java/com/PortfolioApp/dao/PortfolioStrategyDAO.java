package com.PortfolioApp.dao;

import java.util.List;
import java.util.Date;

import com.PortfolioApp.dao.GenericDAO;
import com.PortfolioApp.domain.PortfolioStrategy;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface PortfolioStrategyDAO extends GenericDAO<PortfolioStrategy, Integer> {
  
	List<PortfolioStrategy> findAll();
	


}

