package com.alessandragodoy.accountms.controller.dto;

/**
 * DTO for creating a new account.
 *
 * @param balance the initial balance of the account
 * @param accountType the type of the account
 * @param customerId the ID of the customer
 */
public record CreateAccountDTO(Double balance, String accountType, Integer customerId) {
}
