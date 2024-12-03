package com.alessandragodoy.accountms.controller.dto;

import com.alessandragodoy.accountms.exception.AccountValidationException;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for transactions requests.
 *
 * @param amount the amount to deposit
 */
public record TransactionRequestDTO(
		@Schema(description = "Amount to deposit", example = "100.0")
		Double amount) {
	public TransactionRequestDTO {
		if (amount == null || amount <= 0) {
			throw new AccountValidationException("Balance is a requerid field and must be positive.");
		}
	}
}
