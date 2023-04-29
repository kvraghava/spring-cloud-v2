package com.raghava.currencyconversionservice;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("")
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quan}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,@PathVariable String to, @PathVariable double quan) {
		HashMap<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to",to);
		ResponseEntity<CurrencyConversion> responseEntity = 
				new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		CurrencyConversion currencyConversion = responseEntity.getBody();
		currencyConversion.setQuantity(quan);
		currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple()*quan);
		return currencyConversion;
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quan}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,@PathVariable String to, @PathVariable double quan) {
		CurrencyConversion currencyConversion = proxy.retriveExchangeValue(from, to);
		currencyConversion.setQuantity(quan);
		currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple()*quan);
		return currencyConversion;
	}
}
