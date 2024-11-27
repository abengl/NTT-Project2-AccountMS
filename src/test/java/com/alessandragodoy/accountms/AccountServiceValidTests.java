package com.alessandragodoy.accountms;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.model.Account;
import com.alessandragodoy.accountms.model.AccountType;
import com.alessandragodoy.accountms.repository.AccountRepository;
import com.alessandragodoy.accountms.service.AccountServiceClient;
import com.alessandragodoy.accountms.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for successful scenarios in the AccountService.
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceValidTests {
	public List<Account> accounts = new ArrayList<>();
	@InjectMocks
	private AccountServiceImpl accountService;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private AccountServiceClient accountServiceClient;

	@BeforeEach
	public void setUp() {
		accounts.add(Account.builder()
				.accountId(1).accountNumber("A000000001").balance(1000.0)
				.accountType(AccountType.SAVINGS).customerId(1).build());
		accounts.add(Account.builder()
				.accountId(2).accountNumber("A000000002").balance(1000.0)
				.accountType(AccountType.SAVINGS).customerId(2).build());
		accounts.add(Account.builder()
				.accountId(3).accountNumber("A000000003").balance(1000.0)
				.accountType(AccountType.CHECKING).customerId(2).build());
	}

	@Test
	@DisplayName("Test getAllAccounts method - it returns AccountDTO list")
	void AccountService_GetAllAccounts_ReturnsAccountDTOList() {
		// Arrange
		when(accountRepository.findAll()).thenReturn(accounts);

		// Act
		List<AccountDTO> accountsDTO = accountService.getAllAccounts();

		// Assert
		assertEquals(3, accountsDTO.size());
		assertEquals("A000000001", accountsDTO.get(0).accountNumber());
	}

	@Test
	@DisplayName("Test getAccountById method - it returns an AccountDTO")
	void AccountService_GetAccountById_ReturnsAccountDTO() {
		// Arrange
		int accountId = 1;
		when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(accounts.get(0)));

		// Act
		AccountDTO accountDTO = accountService.getAccountById(accountId);

		// Assert
		assertEquals("A000000001", accountDTO.accountNumber());
	}

	@Test
	@DisplayName("Test createAccount method - it returns AccountDTO")
	void CustomerService_createAccount_ReturnsAccountDTO() {
		// Arrange
		int customerId = 1;
		CreateAccountDTO accountRequest = new CreateAccountDTO(1000.0, "SAVINGS", customerId);

		Account newAccount = Account.builder()
				.accountId(4)
				.accountNumber("A000000004")
				.balance(1000.0)
				.accountType(AccountType.SAVINGS)
				.customerId(customerId)
				.build();

		when(accountServiceClient.customerExists(customerId)).thenReturn(true);
		when(accountRepository.save(any(Account.class))).thenReturn(newAccount);

		// Act
		AccountDTO result = accountService.createAccount(accountRequest);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.customerId());
		assertEquals(1000.0, result.balance());
		verify(accountRepository, times(1)).save(any(Account.class));
	}

	@Test
	@DisplayName("Test deposit method - it returns AccountDTO")
	void CustomerService_deposit_ReturnsAccountDTO() {
		// Arrange
		int accountId = 1;
		Double amount = 100.0;
		Account existingAccount = accounts.get(0);
		when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(existingAccount));
		when(accountRepository.getBalanceByAccountId(accountId)).thenReturn(1100.0);

		// Act
		AccountDTO accountDTO = accountService.deposit(accountId, amount);

		// Assert
		assertNotNull(accountDTO);
		assertEquals(1100.0, accountDTO.balance());
		verify(accountRepository, times(1)).findById(accountId);
		verify(accountRepository, times(1)).updateBalanceDeposit(accountId, amount);
	}

	@Test
	@DisplayName("Test withdraw method - it returns AccountDTO")
	void CustomerService_withdraw_ReturnsAccountDTO() {
		// Arrange
		int accountId = 1;
		Double amount = 100.0;
		Account existingAccount = accounts.get(0);
		Double updatedBalance = existingAccount.getBalance() - amount;
		when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
		doNothing().when(accountRepository).updateBalanceWithdraw(accountId, amount);
		when(accountRepository.getBalanceByAccountId(accountId)).thenReturn(updatedBalance);

		// Act
		AccountDTO accountDTO = accountService.withdraw(accountId, amount);

		// Assert
		assertNotNull(accountDTO);
		assertEquals(updatedBalance, accountDTO.balance());
		verify(accountRepository, times(1)).findById(accountId);
		verify(accountRepository, times(1)).updateBalanceWithdraw(accountId, amount);
		verify(accountRepository, times(1)).getBalanceByAccountId(accountId);
	}

	@Test
	@DisplayName("Test deleteAccountById method - it returns AccountDTO")
	void CustomerService_deleteAccountById_ReturnsAccountDTO() {
		// Arrange
		int accountId = 3;
		Account existingAccount = accounts.get(2);

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
		doNothing().when(accountRepository).delete(existingAccount);

		// Act
		AccountDTO deletedAccount = accountService.deleteAccountById(accountId);

		// Assert
		assertNotNull(deletedAccount);
		assertEquals(3, deletedAccount.accountId());
	}

}
