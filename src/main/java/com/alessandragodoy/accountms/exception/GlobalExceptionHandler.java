package com.alessandragodoy.accountms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * This class handles specific exceptions and returns appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles InsufficientFundsException and returns a 409 Conflict response.
	 *
	 * @param e the InsufficientFundsException
	 * @return a ResponseEntity with a 409 status and the exception message
	 */
	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	/**
	 * Handles AccountNotFoundException and returns a 404 Not Found response.
	 *
	 * @param e the AccountNotFoundException
	 * @return a ResponseEntity with a 404 status and the exception message
	 */
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * Handles ExternalServiceException and returns a 503 Service Unavailable response.
	 *
	 * @param e the ExternalServiceException
	 * @return a ResponseEntity with a 503 status and the exception message
	 */
	@ExceptionHandler(ExternalServiceException.class)
	public ResponseEntity<String> handleExternalServiceException(ExternalServiceException e) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
	}

	/**
	 * Handles AccountValidationException and returns a 400 Bad Request response.
	 *
	 * @param e the AccountValidationException
	 * @return a ResponseEntity with a 400 status and the exception message
	 */
	@ExceptionHandler(AccountValidationException.class)
	public ResponseEntity<String> handleValidationException(AccountValidationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	/**
	 * Handles CustomerNotFoundException and returns a 404 Not Found response.
	 *
	 * @param e the CustomerNotFoundException
	 * @return a ResponseEntity with a 404 status and the exception message
	 */
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}
