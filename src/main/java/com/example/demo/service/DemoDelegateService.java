package com.example.demo.service;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DemoDelegateService {

	Logger logger = LoggerFactory.getLogger(DemoDelegateService.class);

	@Autowired
	RestTemplate restTemplate;
	
	private final String BASE_DATABASE_URI = "http://desktop-q8vqu7f:8083/api/";

	@HystrixCommand(fallbackMethod = "callServiceAndGetDataFallback")
	public <T> ResponseEntity<T> callDataBaseService(HttpMethod httpMethod, Object requestBody, String svcApi,
			Class<T> returnClass) {

		logger.info("Entered callDataBaseService method in DemoDelegateService Class {} {} {} {}", httpMethod, requestBody, svcApi,
				returnClass);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
		return restTemplate.exchange(BASE_DATABASE_URI + svcApi, httpMethod, entity, returnClass);
	}

	@SuppressWarnings("unused")
	private <T> ResponseEntity<T> callServiceAndGetDataFallback(HttpMethod httpMethod, Object requestBody,
			String svcApi, Class<T> returnClass) {

		logger.error("Entered Fall Back method callServiceAndGetData_Fallback in DemoDelegateService class");

		String msg = "CIRCUIT BREAKER ENABLED!!! No Response From database Service at this moment. "
				+ " Service will be back shortly - " + new Date();

		logger.error(msg);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
