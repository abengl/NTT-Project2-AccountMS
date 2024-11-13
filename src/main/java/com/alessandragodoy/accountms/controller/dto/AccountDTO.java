package com.alessandragodoy.accountms.controller.dto;

import com.alessandragodoy.accountms.model.entity.AccountType;

public record AccountDTO(Integer accountId, String accountNumber, Double balance, AccountType accountType,
						 Integer customerId) {
}
