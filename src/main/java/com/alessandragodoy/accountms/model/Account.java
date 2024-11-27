package com.alessandragodoy.accountms.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Entity representing an Account.
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Integer accountId;

	@NotNull
	@Column(name = "account_number", unique = true)
	private String accountNumber;

	@Column(name = "balance", columnDefinition = "double default 0.0")
	private Double balance = 0.0;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountType accountType;

	@NotNull
	@Column(name = "customer_id")
	private Integer customerId;
}
