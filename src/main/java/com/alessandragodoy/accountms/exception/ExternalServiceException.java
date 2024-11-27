package com.alessandragodoy.accountms.exception;

/**
 * Exception thrown when an external service fails.
 */
public class ExternalServiceException extends RuntimeException {
	public ExternalServiceException(String message) {
		super(message);
	}
}
