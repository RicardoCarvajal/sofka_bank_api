package com.sofka.services.app.dto;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class AccountDto {

	private String id;

	@Valid
	@NotNull
	private CustomerDto customer;

	@NotNull
	private BigDecimal globalBalance;

	public AccountDto() {
	}

	public AccountDto(String id, CustomerDto customer, BigDecimal globalBalance) {
		this.id = id;
		this.customer = customer;
		this.globalBalance = globalBalance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public BigDecimal getGlobalBalance() {
		return globalBalance;
	}

	public void setGlobalBalance(BigDecimal globalBalance) {
		this.globalBalance = globalBalance;
	}

	public static AccountDto createAccountDto() {
		return new AccountDto();
	}

	public AccountDto id(String id) {
		this.id = id;
		return this;
	}

	public AccountDto customer(CustomerDto customer) {
		this.customer = customer;
		return this;
	}

	public AccountDto globalBalance(BigDecimal globalBalance) {
		this.globalBalance = globalBalance;
		return this;
	}

	public AccountDto build() {
		return this;
	}

}
