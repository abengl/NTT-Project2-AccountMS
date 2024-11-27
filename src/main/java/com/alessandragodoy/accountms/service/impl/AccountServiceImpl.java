package com.alessandragodoy.accountms.service.impl;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.AccountMapper;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.exception.AccountNotFoundException;
import com.alessandragodoy.accountms.exception.CustomerNotFoundException;
import com.alessandragodoy.accountms.model.Account;
import com.alessandragodoy.accountms.repository.AccountRepository;
import com.alessandragodoy.accountms.service.AccountService;
import com.alessandragodoy.accountms.service.AccountServiceClient;
import com.alessandragodoy.accountms.utility.AccountValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the AccountService interface.
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final AccountServiceClient accountServiceClient;

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
	public AccountDTO createAccount(CreateAccountDTO createAccountDTO) {
		if (!accountServiceClient.customerExists(createAccountDTO.customerId())) {
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
		AccountValidationUtils.validateAmount(amount);

		Account account = findAccountByIdOrThrow(accountId);
		accountRepository.updateBalanceDeposit(accountId, amount);
		account.setBalance(accountRepository.getBalanceByAccountId(accountId));
		return AccountMapper.toDTO(account);
	}

	@Transactional
	@Override
	public AccountDTO withdraw(Integer accountId, Double amount) {
		AccountValidationUtils.validateAmount(amount);

		Account account = findAccountByIdOrThrow(accountId);
		Double currentBalance = account.getBalance();

		AccountValidationUtils.validateSufficientFunds(account, currentBalance, amount);
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

	/* Customer MS and Transaction MS custom methods */
	@Override
	public boolean accountExists(Integer customerId) {
		return accountRepository.existsByCustomerId(customerId);
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

	/* Helper methods */

	private Account findAccountByIdOrThrow(Integer accountId) {
		return accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for ID: " + accountId));
	}

	private String generateAccountNumber() {
		return "A" + (System.currentTimeMillis() / 100);
	}
}
