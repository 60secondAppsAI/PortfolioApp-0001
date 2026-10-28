package com.PortfolioApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="portfolio_balances")
@Getter @Setter @NoArgsConstructor
public class PortfolioBalance {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="portfolio_balance_id")
	private Integer portfolioBalanceId;
    
  	@Column(name="portfolio_id")
	private Integer portfolioId;
    
  	@Column(name="cash_balance")
	private Double cashBalance;
    
  	@Column(name="market_value")
	private Double marketValue;
    
  	@Column(name="total_value")
	private Double totalValue;
    
  	@Column(name="calculated_at")
	private Date calculatedAt;
    

}
