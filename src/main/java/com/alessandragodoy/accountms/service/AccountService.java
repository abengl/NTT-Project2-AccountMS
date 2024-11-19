package com.alessandragodoy.accountms.service;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing accounts.
 */
public interface AccountService {
	/**
	 * Checks if an account exists for a given customer ID.
	 *
	 * @param customerId the ID of the customer
	 * @return true if an account exists, false otherwise
	 */
	boolean accountExists(Integer customerId);

	/**
	 * Retrieves all accounts.
	 *
	 * @return a list of all account DTOs
	 */
	List<AccountDTO> getAllAccounts();

	/**
	 * Retrieves an account by its ID.
	 *
	 * @param accountId the ID of the account
	 * @return an optional containing the account DTO if found, or empty if not found
	 */
	Optional<AccountDTO> getAccountById(Integer accountId);

	/**
	 * Creates a new account.
	 *
	 * @param createAccountDTO the DTO containing account creation details
	 * @return the created account DTO
	 */
	AccountDTO createAccount(CreateAccountDTO createAccountDTO);

	/**
	 * Deposits an amount into an account.
	 *
	 * @param accountId the ID of the account
	 * @param amount the amount to deposit
	 * @return an optional containing the updated account DTO if the account is found, or empty if not found
	 */
	Optional<AccountDTO> deposit(Integer accountId, Double amount);

	/**
	 * Withdraws an amount from an account.
	 *
	 * @param accountId the ID of the account
	 * @param amount the amount to withdraw
	 * @return an optional containing the updated account DTO if the account is found, or empty if not found
	 */
	Optional<AccountDTO> withdraw(Integer accountId, Double amount);

	/**
	 * Deletes an account by its ID.
	 *
	 * @param accountId the ID of the account
	 * @return an optional containing the deleted account DTO if the account is found, or empty if not found
	 */
	Optional<AccountDTO> deleteAccountById(Integer accountId);

	Optional<Double> getAccountBalance(String accountNumber);

	boolean accountExistsByAccountNumber(String accountNumber);

	void updateBalanceByAccountNumber(String accountNumber, Double amount);
}