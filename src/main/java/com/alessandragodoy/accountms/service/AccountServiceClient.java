package com.alessandragodoy.accountms.service;

import com.alessandragodoy.accountms.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Client for interacting with the Customer Microservice.
 */
@Component
public class AccountServiceClient {
	private final RestTemplate restTemplate;
	private final String customerMsUrl;

	public AccountServiceClient(RestTemplate restTemplate, @Value("${customer.ms.url}") String customerMsUrl) {
		this.restTemplate = restTemplate;
		this.customerMsUrl = customerMsUrl;
	}
	public boolean customerExists(Integer customerId) {
		String url = customerMsUrl + "/" + customerId;
		try {
			ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
			return response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody());
		} catch (ResourceAccessException e) {
			throw new ExternalServiceException("Unable to connect to the customer service." + e.getMessage());
		}
	}
}
