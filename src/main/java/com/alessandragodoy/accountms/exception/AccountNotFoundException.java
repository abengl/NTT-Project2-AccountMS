package com.alessandragodoy.accountms.exception;

public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException(String message) {
		super(message);
	}
}
