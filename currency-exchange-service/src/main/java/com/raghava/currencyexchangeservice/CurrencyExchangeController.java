package com.raghava.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

	@GetMapping("/from/{from}/to/{to}")
	public CurrenyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		logger.info("retriveExchangeValue called from {} to {}",from,to);
		CurrenyExchange currenyExchange = repository.findByFromAndTo(from, to);
		if(currenyExchange == null) {
			throw new RuntimeException("unable to fing data for:"+from+" to:"+to);
		}
		currenyExchange.setEnvironment(environment.getProperty("local.server.port"));		
		return currenyExchange;
	}
}
