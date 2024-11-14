package com.alessandragodoy.accountms.exception;

/**
 * Exception thrown when an account has insufficient funds for a transaction.
 */
public class InsufficientFundsException extends RuntimeException {
	public InsufficientFundsException(String message) {
		super(message);
	}
}
