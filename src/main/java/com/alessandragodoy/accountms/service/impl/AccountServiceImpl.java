package com.alessandragodoy.accountms.service.impl;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.AccountMapper;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.exception.*;
import com.alessandragodoy.accountms.model.entity.Account;
import com.alessandragodoy.accountms.model.entity.AccountType;
import com.alessandragodoy.accountms.repository.AccountRepository;
import com.alessandragodoy.accountms.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the AccountService interface.
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final RestTemplate restTemplate;

	@Value("${customer.ms.url}")
	private String customerMsUrl;

	/* Account Service methods */
	@Override
	public List<AccountDTO> getAllAccounts() {
		return accountRepository.findAll().stream().map(AccountMapper::toDTO).toList();
	}

	@Override
	public AccountDTO getAccountById(Integer accountId) {
		return accountRepository.findById(accountId).map(AccountMapper::toDTO)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for ID: " + accountId));
	}

	@Override
	public boolean accountExists(Integer customerId) {
		return accountRepository.existsByCustomerId(customerId);
	}

	@Override
	public AccountDTO createAccount(CreateAccountDTO createAccountDTO) {
		if (!customerExists(createAccountDTO.customerId())) {
			throw new CustomerNotFoundException("Customer not found for ID: " + createAccountDTO.customerId());
		}
		Account newAccount = AccountMapper.toCreateEntity(createAccountDTO);
		newAccount.setAccountNumber(generateAccountNumber());
		accountRepository.save(newAccount);
		return AccountMapper.toDTO(newAccount);
	}

	@Transactional
	@Override
	public AccountDTO deposit(Integer accountId, Double amount) {
		validateAmount(amount);

		Account account = findAccountByIdOrThrow(accountId);
		accountRepository.updateBalanceDeposit(accountId, amount);
		account.setBalance(accountRepository.getBalanceByAccountId(accountId));
		return AccountMapper.toDTO(account);
	}

	@Transactional
	@Override
	public AccountDTO withdraw(Integer accountId, Double amount) {
		validateAmount(amount);

		Account account = findAccountByIdOrThrow(accountId);
		Double currentBalance = account.getBalance();

		validateSufficientFunds(account, currentBalance, amount);
		accountRepository.updateBalanceWithdraw(accountId, amount);
		account.setBalance(accountRepository.getBalanceByAccountId(accountId));
		return AccountMapper.toDTO(account);
	}

	@Override
	public AccountDTO deleteAccountById(Integer accountId) {
		return accountRepository.findById(accountId).map(existingAccount -> {
			accountRepository.delete(existingAccount);
			return AccountMapper.toDTO(existingAccount);
		}).orElseThrow(() -> new AccountNotFoundException("Account not found for ID: " + accountId));
	}


	/* Helper methods */
	private boolean customerExists(Integer customerId) {
		String url = customerMsUrl + "/" + customerId;
		try {
			ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				return response.getBody();
			}
		} catch (ResourceAccessException e) {
			throw new ExternalServiceException("Unable to connect to the customer service.");
		}
		throw new ExternalServiceException("Unexpected error occurred while checking customer accounts.");
	}

	private String generateAccountNumber() {
		return "A" + (System.currentTimeMillis() / 100);
	}

	private void validateSufficientFunds(Account account, Double currentBalance, Double amount) {
		if (account.getAccountType() == AccountType.SAVINGS && currentBalance < amount) {
			throw new InsufficientFundsException("Insufficient funds for withdrawal.");
		} else if (account.getAccountType() == AccountType.CHECKING && currentBalance - amount < -500) {
			throw new InsufficientFundsException("Insufficient funds for withdrawal. Account overdraft limit reached" +
					".");
		}
	}

	private void validateAmount(Double amount) {
		if (amount == null || amount <= 0) {
			throw new AccountValidationException("Amount must be greater than zero.");
		}
	}

	private Account findAccountByIdOrThrow(Integer accountId) {
		return accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for ID: " + accountId));
	}

	@Override
	public Double getAccountBalance(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber)
				.map(Account::getBalance)
				.orElseThrow(() ->
				new AccountNotFoundException("Account not found for number: " + accountNumber));
	}

	@Override
	public boolean accountExistsByAccountNumber(String accountNumber) {
		return accountRepository.existsByAccountNumber(accountNumber);
	}

	@Override
	public void updateBalanceByAccountNumber(String accountNumber, Double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for number: " + accountNumber));
		account.setBalance(account.getBalance() + amount);
		accountRepository.save(account);
	}
}
