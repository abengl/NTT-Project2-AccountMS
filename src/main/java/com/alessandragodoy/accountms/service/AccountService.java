package com.alessandragodoy.accountms.service;

import com.alessandragodoy.accountms.controller.dto.AccountDTO;
import com.alessandragodoy.accountms.controller.dto.CreateAccountDTO;

import java.util.List;
import java.util.Optional;

public interface AccountService {
	List<AccountDTO> getAllAccounts();
	Optional<AccountDTO> getAccountById(Integer accountId);
	AccountDTO createAccount(CreateAccountDTO createAccountDTO);
	Optional<AccountDTO> deposit(Integer accountId, Double amount);
	Optional<AccountDTO> withdraw(Integer accountId, Double amount);
	Optional<AccountDTO> deleteAccountById(Integer accountId);

}
