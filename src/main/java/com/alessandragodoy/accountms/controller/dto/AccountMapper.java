package com.alessandragodoy.accountms.controller.dto;

import com.alessandragodoy.accountms.model.entity.Account;
import com.alessandragodoy.accountms.model.entity.AccountType;

public class AccountMapper {
	public static AccountDTO toDTO(Account account) {
		return new AccountDTO(
				account.getAccountId(),
				account.getAccountNumber(),
				account.getBalance(),
				account.getAccountType(),
				account.getCustomerId()
		);
	}

	public static Account toEntity(AccountDTO accountDTO) {
		return new Account(
				accountDTO.accountId(),
				accountDTO.accountNumber(),
				accountDTO.balance(),
				accountDTO.accountType(),
				accountDTO.customerId()
		);
	}

	public static Account toCreateEntity(CreateAccountDTO createAccountDTO) {
		return new Account(
				null,
				null,
				createAccountDTO.balance(),
				AccountType.valueOf(createAccountDTO.accountType().toUpperCase()),
				createAccountDTO.customerId()
		);
	}
}
