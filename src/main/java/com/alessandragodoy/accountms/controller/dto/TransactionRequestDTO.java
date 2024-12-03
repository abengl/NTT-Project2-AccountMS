package com.alessandragodoy.accountms.controller.dto;

import com.alessandragodoy.accountms.exception.AccountValidationException;

/**
 * DTO for transactions requests.
 *
 * @param amount the amount to deposit
 */
public record TransactionRequestDTO(Double amount) {
	public TransactionRequestDTO {
		if (amount == null || amount <= 0) {
			throw new AccountValidationException("Balance is a requerid field and must be positive.");
		}
	}
}
