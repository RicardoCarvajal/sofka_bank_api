package com.sofka.services.app.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class TransactionDto {

	private String id;

	@NotNull
	@NotEmpty
	private String idAccount;

	@NotNull
	private BigDecimal amountTransaction;

	@NotNull
	private BigDecimal initBalence;

	@NotNull
	private BigDecimal finalBalance;

	@NotNull
	private BigDecimal transactionCost;

	@NotNull
	@NotEmpty
	private String type;

	public TransactionDto() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public BigDecimal getAmountTransaction() {
		return amountTransaction;
	}

	public void setAmountTransaction(BigDecimal amountTransaction) {
		this.amountTransaction = amountTransaction;
	}

	public BigDecimal getInitBalence() {
		return initBalence;
	}

	public void setInitBalence(BigDecimal initBalence) {
		this.initBalence = initBalence;
	}

	public BigDecimal getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(BigDecimal finalBalance) {
		this.finalBalance = finalBalance;
	}

	public BigDecimal getTransactionCost() {
		return transactionCost;
	}

	public void setTransactionCost(BigDecimal transactionCost) {
		this.transactionCost = transactionCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static TransactionDto createTransactionDto() {
		return new TransactionDto();
	}

	public TransactionDto id(String id) {
		this.id = id;
		return this;
	}

	public TransactionDto account(String idAccount) {
		this.idAccount = idAccount;
		return this;
	}

	public TransactionDto amountTransaction(BigDecimal amountTransaction) {
		this.amountTransaction = amountTransaction;
		return this;
	}

	public TransactionDto initBalence(BigDecimal initBalence) {
		this.initBalence = initBalence;
		return this;
	}

	public TransactionDto finalBalance(BigDecimal finalBalance) {
		this.finalBalance = finalBalance;
		return this;
	}

	public TransactionDto transactionCost(BigDecimal transactionCost) {
		this.transactionCost = transactionCost;
		return this;
	}

	public TransactionDto type(String type) {
		this.type = type;
		return this;
	}

	public TransactionDto build() {
		return this;
	}

}
