package com.alessandragodoy.accountms.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for creating a new account.
 *
 * @param balance     the initial balance of the account
 * @param accountType the type of the account
 * @param customerId  the ID of the customer
 */
public record CreateAccountDTO(
		@Schema(description = "Initial balance of the account", example = "100.0")
		Double balance,
		@Schema(description = "Type of the account", example = "SAVINGS")
		String accountType,
		@Schema(description = "Unique identifier for the customer", example = "1")
		Integer customerId) {
}
