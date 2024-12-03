package com.alessandragodoy.accountms;

import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;
import com.alessandragodoy.accountms.exception.AccountNotFoundException;
import com.alessandragodoy.accountms.exception.AccountValidationException;
import com.alessandragodoy.accountms.exception.CustomerNotFoundException;
import com.alessandragodoy.accountms.exception.InsufficientFundsException;
import com.alessandragodoy.accountms.model.Account;
import com.alessandragodoy.accountms.model.AccountType;
import com.alessandragodoy.accountms.repository.AccountRepository;
import com.alessandragodoy.accountms.service.impl.AccountServiceImpl;
import com.alessandragodoy.accountms.utility.AccountValidation;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for exception scenarios in the AccountService.
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceExceptionTests {
	public List<Account> accounts = new ArrayList<>();
	@InjectMocks
	private AccountServiceImpl accountService;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private AccountValidation accountValidation;

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
	@DisplayName("Test getAccountById method - it returns an AccountNotFoundException when not found")
	void AccountService_GetAccountById_ReturnsException() {
		// Arrange
		int accountId = 100;
		when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

		// Act
		AccountNotFoundException exception =
				assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(accountId));

		// Assert
		assertEquals("The account with ID " + accountId + " does not exist.", exception.getMessage());
	}

	@Test
	@DisplayName("Test createAccount method - it returns CustomerNotFoundException when customer doesn't exist")
	void CustomerService_createAccount_ReturnsExceptionNotFound() {
		// Arrange
		int customerId = 10;
		CreateAccountDTO accountRequest = new CreateAccountDTO(1000.0, "SAVINGS", customerId);
		doNothing().when(accountValidation).validateAccountData(accountRequest);
		doThrow(new CustomerNotFoundException("Customer not found for ID: " + customerId)).when(accountValidation)
				.validateCustomerExists(customerId);

		// Act & Assert
		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
				() -> accountService.createAccount(accountRequest));
		assertEquals("Customer not found for ID: " + customerId, exception.getMessage());

	}

	@Test
	@DisplayName("Test deposit method - it returns AccountValidationException for invalid amount")
	void CustomerService_deposit_ReturnsException() {
		// Arrange
		int accountId = 1;
		Double invalidAmount = -100.0;

		// Act & Assert
		AccountValidationException exception = assertThrows(AccountValidationException.class,
				() -> accountService.deposit(accountId, invalidAmount));
		assertEquals("Amount must be greater than zero.", exception.getMessage());
	}

	@Test
	@DisplayName("Test deposit method - it returns AccountNotFoundException")
	void CustomerService_deposit_ReturnsExceptionNotFound() {
		// Arrange
		int accountId = 10;
		Double amount = 100.0;

		when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
		// Act & Assert
		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class,
				() -> accountService.deposit(accountId, amount));
		assertEquals("Deposit can not continue. Account not found for ID: " + accountId, exception.getMessage());
	}

	@Test
	@DisplayName("Test withdraw method - it returns InsufficientFundsException for savings account")
	void CustomerService_withdraw_ReturnsExceptionSavings() {
		// Arrange
		int accountId = 1;
		Double amount = 2000.0;
		Account existingAccount = accounts.get(0);

		when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(existingAccount));

		// Act & Assert
		InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
				() -> accountService.withdraw(accountId, amount));
		assertEquals("Insufficient funds for withdrawal.", exception.getMessage());
	}

	@Test
	@DisplayName("Test withdraw method - it returns InsufficientFundsException for checking account")
	void CustomerService_withdraw_ReturnsExceptionChecking() {
		// Arrange
		int accountId = 3;
		Double amount = 2000.0;
		Account existingAccount = accounts.get(2);

		when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(existingAccount));

		// Act & Assert
		InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
				() -> accountService.withdraw(accountId, amount));
		assertEquals("Insufficient funds for withdrawal. Account overdraft limit reached.", exception.getMessage());
	}

	@Test
	@DisplayName("Test deleteAccountById method - it returns AccountNotFoundException")
	void CustomerService_deleteAccountById_ReturnsExceptionNotFound() {
		// Arrange
		int accountId = 10;

		when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

		// Act & Assert
		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class,
				() -> accountService.deleteAccountById(accountId));
		assertEquals("Delete stopped. Account not found for ID: " + accountId, exception.getMessage());
	}
}
