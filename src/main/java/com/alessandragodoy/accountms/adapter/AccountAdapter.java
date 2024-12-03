package com.alessandragodoy.accountms.adapter;

/**
 * Adapter interface for interacting with Customer microservice.
 * <br>
 * This interface follows the <b>Adapter design pattern</b>, which
 * allows the application to interact with external services
 * through a consistent interface, decoupling the service implementation from the application logic.
 */
public interface AccountAdapter {
	boolean customerExists(Integer customerId);
}
