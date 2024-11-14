package com.alessandragodoy.accountms.controller;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.accountms.controller.dto.WithdrawalRequestDTO;
import com.alessandragodoy.accountms.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
	private final AccountService accountService;

	@GetMapping
	public ResponseEntity<?> getAllAccounts() {
		List<AccountDTO> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<?> getAccountById(@PathVariable Integer accountId) {
		Optional<AccountDTO> account = accountService.getAccountById(accountId);
		return account.isPresent() ? ResponseEntity.ok(account.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping("/customer/{customerId}")
	public boolean getAccountByCustomerId(@PathVariable Integer customerId) {
		return accountService.accountExists(customerId);
	}

	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
		AccountDTO account = accountService.createAccount(createAccountDTO);
		return ResponseEntity.ok(account);
	}

	@PutMapping("/{accountId}/deposit")
	public ResponseEntity<?> deposit(@PathVariable Integer accountId,
									 @RequestBody DepositRequestDTO depositRequestDTO) {
		Optional<AccountDTO> updatedAccount = accountService.deposit(accountId, depositRequestDTO.amount());
		return updatedAccount.isPresent() ? ResponseEntity.ok(updatedAccount.get()) :
				ResponseEntity.notFound().build();
	}

	@PutMapping("/{accountId}/withdraw")
	public ResponseEntity<?> withdraw(@PathVariable Integer accountId,
									  @RequestBody WithdrawalRequestDTO withdrawalRequestDTO) {
		Optional<AccountDTO> updatedAccount = accountService.withdraw(accountId, withdrawalRequestDTO.amount());
		return updatedAccount.isPresent() ? ResponseEntity.ok(updatedAccount.get()) :
				ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{accountId}")
	public ResponseEntity<?> deleteAccountById(@PathVariable Integer accountId) {
		Optional<AccountDTO> deletedAccount = accountService.deleteAccountById(accountId);
		return deletedAccount.isPresent() ? ResponseEntity.ok(deletedAccount.get()) :
				ResponseEntity.notFound().build();
	}

}
