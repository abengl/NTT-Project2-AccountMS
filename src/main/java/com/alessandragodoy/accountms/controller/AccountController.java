package com.alessandragodoy.accountms.controller;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.controller.dto.TransactionRequestDTO;
import com.alessandragodoy.accountms.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing accounts.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
	private final AccountService accountService;

	/**
	 * Retrieves all accounts.
	 *
	 * @return a ResponseEntity containing a list of AccountDTO objects.
	 */
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		List<AccountDTO> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}

	/**
	 * Retrieves an account by its ID.
	 *
	 * @param accountId the ID of the account to retrieve.
	 * @return a ResponseEntity containing the AccountDTO object.
	 */
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable Integer accountId) {
		AccountDTO account = accountService.getAccountById(accountId);
		return ResponseEntity.ok(account);
	}

	/**
	 * Creates a new account.
	 *
	 * @param createAccountDTO the data transfer object containing the account details.
	 * @return a ResponseEntity containing the created AccountDTO object.
	 */
	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
		AccountDTO account = accountService.createAccount(createAccountDTO);
		return ResponseEntity.ok(account);
	}

	/**
	 * Deposits an amount into the account with the given ID.
	 *
	 * @param accountId         the ID of the account to deposit into.
	 * @param transactionRequestDTO the data transfer object containing the deposit amount.
	 * @return a ResponseEntity containing the updated AccountDTO object.
	 */
	@PutMapping("/deposit/{accountId}")
	public ResponseEntity<AccountDTO> deposit(@PathVariable Integer accountId,
											  @RequestBody TransactionRequestDTO transactionRequestDTO) {
		AccountDTO updatedAccount = accountService.deposit(accountId, transactionRequestDTO.amount());
		return ResponseEntity.ok(updatedAccount);
	}

	/**
	 * Withdraws an amount from the account with the given ID.
	 *
	 * @param accountId            the ID of the account to withdraw from.
	 * @param transactionRequestDTO the data transfer object containing the withdrawal amount.
	 * @return a ResponseEntity containing the updated AccountDTO object.
	 */
	@PutMapping("/withdraw/{accountId}")
	public ResponseEntity<AccountDTO> withdraw(@PathVariable Integer accountId,
											   @RequestBody TransactionRequestDTO transactionRequestDTO) {
		AccountDTO updatedAccount = accountService.withdraw(accountId, transactionRequestDTO.amount());
		return ResponseEntity.ok(updatedAccount);
	}

	/**
	 * Deletes an account by its ID.
	 *
	 * @param accountId the ID of the account to delete.
	 * @return a ResponseEntity containing the deleted AccountDTO object.
	 */
	@DeleteMapping("/{accountId}")
	public ResponseEntity<AccountDTO> deleteAccountById(@PathVariable Integer accountId) {
		AccountDTO deletedAccount = accountService.deleteAccountById(accountId);
		return ResponseEntity.ok(deletedAccount);
	}

	/**
	 * Checks if an account exists for a given customer ID.
	 *
	 * @param customerId the ID of the customer
	 * @return true if the account exists, false otherwise
	 */
	@GetMapping("/customer/{customerId}")
	public boolean getAccountByCustomerId(@PathVariable Integer customerId) {
		return accountService.accountExists(customerId);
	}

	/**
	 * Retrieves the balance of an account by its account number.
	 *
	 * @param accountNumber the account number of the account to retrieve the balance for.
	 * @return a ResponseEntity containing the account balance.
	 */
	@GetMapping("/balance/{accountNumber}")
	public ResponseEntity<Double> getAccountBalance(@PathVariable String accountNumber) {
		Double balance = accountService.getAccountBalance(accountNumber);
		return ResponseEntity.ok(balance);
	}

	/**
	 * Verifies if an account exists by its account number.
	 *
	 * @param accountNumber the account number to verify.
	 * @return a ResponseEntity containing a boolean value indicating if the account exists.
	 */
	@GetMapping("/verify/{accountNumber}")
	public ResponseEntity<Boolean> verifyAccountByAccountNumber(@PathVariable String accountNumber) {
		boolean exists = accountService.accountExistsByAccountNumber(accountNumber);
		return ResponseEntity.ok(exists);
	}

	/**
	 * Updates the balance of an account by its account number.
	 *
	 * @param accountNumber the account number of the account to update the balance for.
	 * @param amount        the amount to update the balance by.
	 * @return a ResponseEntity indicating the success of the operation.
	 */
	@PatchMapping("/update/{accountNumber}")
	public ResponseEntity<String> updateBalanceByAccountNumber(@PathVariable String accountNumber,
															 @RequestParam Double amount) {
		accountService.updateBalanceByAccountNumber(accountNumber, amount);
		return ResponseEntity.ok("Balance updated successfully");
	}

}
