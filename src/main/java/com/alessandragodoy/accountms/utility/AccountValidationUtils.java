package com.alessandragodoy.accountms.utility;

import com.alessandragodoy.accountms.exception.AccountValidationException;
import com.alessandragodoy.accountms.exception.InsufficientFundsException;
import com.alessandragodoy.accountms.model.Account;
import com.alessandragodoy.accountms.model.AccountType;

/**
 * Utility class for validating account-related operations.
 */
public class AccountValidationUtils {
	private AccountValidationUtils() {
	}

	public static void validateAmount(Double amount) {
		if (amount == null || amount <= 0) {
			throw new AccountValidationException("Amount must be greater than zero.");
		}
	}

	public static void validateSufficientFunds(Account account, Double currentBalance, Double amount) {
		if (account.getAccountType() == AccountType.SAVINGS && currentBalance < amount) {
			throw new InsufficientFundsException("Insufficient funds for withdrawal.");
		} else if (account.getAccountType() == AccountType.CHECKING && currentBalance - amount < -500) {
			throw new InsufficientFundsException("Insufficient funds for withdrawal. Account overdraft limit reached.");
		}
	}

}
