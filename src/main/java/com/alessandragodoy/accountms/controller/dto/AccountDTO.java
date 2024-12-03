package com.alessandragodoy.accountms.controller.dto;

import com.alessandragodoy.accountms.model.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for Account.
 *
 * @param accountId     the ID of the account
 * @param accountNumber the number of the account
 * @param balance       the balance of the account
 * @param accountType   the type of the account
 * @param customerId    the ID of the customer
 */
public record AccountDTO(
		@Schema(description = "Unique identifier for the account", example = "1")
		Integer accountId,
		@Schema(description = "Account number", example = "A000001")
		String accountNumber,
		@Schema(description = "Current balance of the account", example = "100.0")
		Double balance,
		@Schema(description = "Type of the account", example = "SAVINGS")
		AccountType accountType,
		@Schema(description = "Unique identifier for the customer", example = "1")
		Integer customerId) {
}
