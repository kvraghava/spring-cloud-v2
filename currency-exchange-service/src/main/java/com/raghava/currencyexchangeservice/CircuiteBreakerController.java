package com.raghava.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuiteBreakerController {

	private Logger logger= LoggerFactory.getLogger(CircuiteBreakerController.class);
	
	@GetMapping("/sample-api")
	//@Retry(name="sample-api", fallbackMethod="hardCodedResponse")
	@CircuitBreaker(name="sample-api", fallbackMethod="hardCodedResponse")
	public String sampleApi() {
		logger.info("sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class);
		return forEntity.getBody();
	}
	
	public String hardCodedResponse(Exception ex) {
		return "hard coded Response";
	}
	
	@GetMapping("/sample-api2")
	@RateLimiter(name="default")
	public String sampleApi2() {
		logger.info("sample api 2 call received");
		return "Sample-api 2";
	}
}
