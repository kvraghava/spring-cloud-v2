package com.raghava.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrenyExchange, Long> {
	
	public CurrenyExchange findByFromAndTo(String from, String to);
}
