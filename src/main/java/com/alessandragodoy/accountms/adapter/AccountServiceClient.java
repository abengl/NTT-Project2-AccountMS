package com.alessandragodoy.accountms.adapter;

import com.alessandragodoy.accountms.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Client for interacting with the Customer Microservice.
 * <p>
 * This class uses the <b>Singleton design pattern</b> to ensure that only one instance of the client exists.
 * </p>
 */
@Component
public class AccountServiceClient {
	private final RestTemplate restTemplate;
	private final String customerMsUrl;

	public AccountServiceClient(RestTemplate restTemplate, @Value("${customer.ms.url}") String customerMsUrl) {
		this.restTemplate = restTemplate;
		this.customerMsUrl = customerMsUrl;
	}


	/**
	 * Checks if a customer exists by their ID.
	 *
	 * @param customerId the ID of the customer to check
	 * @return true if the customer exists, false otherwise
	 * @throws ExternalServiceException if unable to connect to the customer service
	 */
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
