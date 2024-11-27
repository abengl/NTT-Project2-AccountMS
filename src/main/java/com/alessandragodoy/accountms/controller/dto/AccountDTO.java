package com.alessandragodoy.accountms.controller.dto;

import com.alessandragodoy.accountms.model.AccountType;

/**
 * Data Transfer Object for Account.
 *
 * @param accountId    the ID of the account
 * @param accountNumber the number of the account
 * @param balance      the balance of the account
 * @param accountType  the type of the account
 * @param customerId   the ID of the customer
 */
public record AccountDTO(Integer accountId, String accountNumber, Double balance, AccountType accountType,
						 Integer customerId) {
}
