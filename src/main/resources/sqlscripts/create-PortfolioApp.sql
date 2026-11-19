CREATE DATABASE IF NOT EXISTS PortfolioApp;

CREATE TABLE PortfolioApp.portfolios (
    `portfolio_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `description` VARCHAR(255),
    `base_currency` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (portfolio_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PortfolioApp.portfolio_accounts (
    `portfolio_account_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `account_number` VARCHAR(255),
    `account_type` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (portfolio_account_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PortfolioApp.portfolio_balances (
    `portfolio_balance_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `cash_balance` DOUBLE,
    `market_value` DOUBLE,
    `total_value` DOUBLE,
    `calculated_at` DATETIME,
PRIMARY KEY (portfolio_balance_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PortfolioApp.portfolio_strategys (
    `portfolio_strategy_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `strategy_id` INT,
    `allocation_percent` DOUBLE,
PRIMARY KEY (portfolio_strategy_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
