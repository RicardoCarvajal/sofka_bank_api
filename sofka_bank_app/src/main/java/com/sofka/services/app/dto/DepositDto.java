package com.sofka.services.app.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class DepositDto {

	@NotNull
	@NotEmpty
	private String idAccount;

	@NotNull
	private BigDecimal amount;

	@NotNull
	@NotEmpty
	private String idOrigin;

	public DepositDto() {

	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getIdOrigin() {
		return idOrigin;
	}

	public void setIdOrigin(String idOrigin) {
		this.idOrigin = idOrigin;
	}

	public static DepositDto createDepositDto() {
		return new DepositDto();
	}

	public DepositDto idAccount(String idAccount) {
		this.idAccount = idAccount;
		return this;
	}

	public DepositDto amount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public DepositDto idOrigin(String idOrigin) {
		this.idOrigin = idOrigin;
		return this;
	}

	public DepositDto build() {
		return this;
	}

}
