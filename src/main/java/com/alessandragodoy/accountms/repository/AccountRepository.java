package com.alessandragodoy.accountms.repository;

import com.alessandragodoy.accountms.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Modifying
	@Query("UPDATE Account a SET a.balance = a.balance + :amount WHERE a.accountId = :accountId")
	void updateBalanceDeposit(@Param("accountId") Integer accountId, @Param("amount") Double amount);

	@Modifying
	@Query("UPDATE Account a SET a.balance = a.balance - :amount WHERE a.accountId = :accountId")
	void updateBalanceWithdraw(@Param("accountId") Integer accountId, @Param("amount") Double amount);

	@Query("SELECT a.balance FROM Account a WHERE a.accountId = :accountId")
	Double getBalanceByAccountId(@Param("accountId") Integer accountId);
}
