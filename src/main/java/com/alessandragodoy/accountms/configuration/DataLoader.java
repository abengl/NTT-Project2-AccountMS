package com.alessandragodoy.accountms.configuration;

import com.alessandragodoy.accountms.model.entity.Account;
import com.alessandragodoy.accountms.model.entity.AccountType;
import com.alessandragodoy.accountms.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataLoader is a component that initializes the database with some initial account data
 * if no accounts are found in the repository.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	private final AccountRepository accountRepository;

	@Override
	public void run(String... args) {
		if (accountRepository.count() == 0) {
			System.out.println("No accounts found, creating initial accounts...");
			List<Account> initialAccounts =List.of(
					Account.builder().accountNumber("A00000000001").balance(1000.0).accountType(AccountType.SAVINGS).customerId(1).build(),
					Account.builder().accountNumber("A00000000002").balance(1000.0).accountType(AccountType.CHECKING).customerId(1).build(),
					Account.builder().accountNumber("A00000000003").balance(1000.0).accountType(AccountType.SAVINGS).customerId(2).build(),
					Account.builder().accountNumber("A00000000004").balance(1000.0).accountType(AccountType.CHECKING).customerId(3).build(),
					Account.builder().accountNumber("A00000000005").balance(1000.0).accountType(AccountType.SAVINGS).customerId(5).build()
			);
			accountRepository.saveAll(initialAccounts);
			System.out.println("Initial accounts added to the database.");
		} else {
			System.out.println("Accounts already exist, skipping initialization.");
		}
	}
}
