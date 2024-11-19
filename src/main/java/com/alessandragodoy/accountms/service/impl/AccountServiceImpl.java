package com.alessandragodoy.accountms.service.impl;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.AccountMapper;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.exception.AccountNotFoundException;
import com.alessandragodoy.accountms.exception.CustomerNotFoundException;
import com.alessandragodoy.accountms.exception.InsufficientFundsException;
import com.alessandragodoy.accountms.model.entity.Account;
import com.alessandragodoy.accountms.model.entity.AccountType;
import com.alessandragodoy.accountms.repository.AccountRepository;
import com.alessandragodoy.accountms.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;
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
	public Optional<AccountDTO> getAccountById(Integer accountId) {
		return accountRepository.findById(accountId).map(AccountMapper::toDTO);
	}

	@Override
	public boolean accountExists(Integer customerId) {
		return accountRepository.existsByCustomerId(customerId);
	}

	@Override
	public AccountDTO createAccount(CreateAccountDTO createAccountDTO) {
		if (!customerExists(createAccountDTO.customerId())) {
			throw new ValidationException("Customer not found for ID: " + createAccountDTO.customerId());
		}
		Account newAccount = AccountMapper.toCreateEntity(createAccountDTO);
		newAccount.setAccountNumber(generateAccountNumber());
		accountRepository.save(newAccount);
		return AccountMapper.toDTO(newAccount);
	}

	@Transactional
	@Override
	public Optional<AccountDTO> deposit(Integer accountId, Double amount) {
		validateAmount(amount);

		Account account = findAccountByIdOrThrow(accountId);
		accountRepository.updateBalanceDeposit(accountId, amount);
		account.setBalance(accountRepository.getBalanceByAccountId(accountId));
		return Optional.of(AccountMapper.toDTO(account));
	}

	@Transactional
	@Override
	public Optional<AccountDTO> withdraw(Integer accountId, Double amount) {
		validateAmount(amount);

		Account account = findAccountByIdOrThrow(accountId);
		Double currentBalance = account.getBalance();

		validateSufficientFunds(account, currentBalance, amount);
		accountRepository.updateBalanceWithdraw(accountId, amount);
		account.setBalance(accountRepository.getBalanceByAccountId(accountId));
		return Optional.of(AccountMapper.toDTO(account));
	}

	@Override
	public Optional<AccountDTO> deleteAccountById(Integer accountId) {
		return accountRepository.findById(accountId).map(existingAccount -> {
			accountRepository.delete(existingAccount);
			return AccountMapper.toDTO(existingAccount);
		});
	}


	/* Helper methods */
	private boolean customerExists(Integer customerId) {
		String url = customerMsUrl + "/" + customerId;
		try {
			ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				return response.getBody();
			}
		} catch (Exception e) {
			System.out.println("Error calling customer-ms: " + e.getMessage());
		}
		throw new CustomerNotFoundException("Customer not found for ID: " + customerId);
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
			throw new ValidationException("Amount must be greater than zero.");
		}
	}

	private Account findAccountByIdOrThrow(Integer accountId) {
		return accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for ID: " + accountId));
	}

	@Override
	public Optional<Double> getAccountBalance(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber).map(Account::getBalance);
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
