package com.alessandragodoy.accountms.utility;

import org.springframework.stereotype.Component;

/**
 * Component responsible for generating unique account numbers.
 */
@Component
public class AccountNumberGenerator {
	public String generate() {
		return "A" + (System.currentTimeMillis() / 100);
	}
}
