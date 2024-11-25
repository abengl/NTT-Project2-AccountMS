package com.alessandragodoy.accountms.controller;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.accountms.controller.dto.WithdrawalRequestDTO;
import com.alessandragodoy.accountms.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing accounts.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Controller for Account")
public class AccountController {
	private final AccountService accountService;

	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		List<AccountDTO> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable Integer accountId) {
		AccountDTO account = accountService.getAccountById(accountId);
		return ResponseEntity.ok(account);
	}

	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
		AccountDTO account = accountService.createAccount(createAccountDTO);
		return ResponseEntity.ok(account);
	}

	@PutMapping("/{accountId}/deposit")
	public ResponseEntity<AccountDTO> deposit(@PathVariable Integer accountId,
									 @RequestBody DepositRequestDTO depositRequestDTO) {
		AccountDTO updatedAccount = accountService.deposit(accountId, depositRequestDTO.amount());
		return ResponseEntity.ok(updatedAccount);
	}

	@PutMapping("/{accountId}/withdraw")
	public ResponseEntity<AccountDTO> withdraw(@PathVariable Integer accountId,
									  @RequestBody WithdrawalRequestDTO withdrawalRequestDTO) {
		AccountDTO updatedAccount = accountService.withdraw(accountId, withdrawalRequestDTO.amount());
		return ResponseEntity.ok(updatedAccount);
	}

	@DeleteMapping("/{accountId}")
	public ResponseEntity<AccountDTO> deleteAccountById(@PathVariable Integer accountId) {
		AccountDTO deletedAccount = accountService.deleteAccountById(accountId);
		return ResponseEntity.ok(deletedAccount);
	}

	@GetMapping("/customer/{customerId}")
	public boolean getAccountByCustomerId(@PathVariable Integer customerId) {
		return accountService.accountExists(customerId);
	}

	@GetMapping("/balance/{accountNumber}")
	public ResponseEntity<Double> getAccountBalance(@PathVariable String accountNumber) {
		Double balance = accountService.getAccountBalance(accountNumber);
		return ResponseEntity.ok(balance);
	}
	@GetMapping("/verify/{accountNumber}")
	public ResponseEntity<Boolean> verifyAccountByAccountNumber(@PathVariable String accountNumber) {
		boolean exists = accountService.accountExistsByAccountNumber(accountNumber);
		return ResponseEntity.ok(exists);
	}

	@PatchMapping("/update/{accountNumber}")
	public ResponseEntity<Void> updateBalanceByAccountNumber(@PathVariable String accountNumber, @RequestParam Double amount) {
		accountService.updateBalanceByAccountNumber(accountNumber, amount);
		return ResponseEntity.ok().build();
	}

}
