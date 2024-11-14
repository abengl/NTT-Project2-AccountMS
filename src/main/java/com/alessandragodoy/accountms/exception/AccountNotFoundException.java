package com.alessandragodoy.accountms.exception;

/**
 * Exception thrown when an account is not found.
 */
public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException(String message) {
		super(message);
	}
}
