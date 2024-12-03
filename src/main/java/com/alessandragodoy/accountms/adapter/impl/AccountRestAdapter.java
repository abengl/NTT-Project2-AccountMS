package com.alessandragodoy.accountms.adapter.impl;

import com.alessandragodoy.accountms.adapter.AccountAdapter;
import com.alessandragodoy.accountms.adapter.AccountServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation of the AccountAdapter interface for interacting with the Customer microservice.
 */
@Component
@RequiredArgsConstructor
public class AccountRestAdapter implements AccountAdapter {
	private final AccountServiceClient accountServiceClient;

	@Override
	public boolean customerExists(Integer customerId) {
		return accountServiceClient.customerExists(customerId);
	}
}
