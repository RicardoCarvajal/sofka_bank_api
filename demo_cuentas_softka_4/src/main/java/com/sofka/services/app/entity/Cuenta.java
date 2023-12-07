package com.sofka.services.app.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cuentas")
public class Cuenta {

	@Id
	private String id;
	private BigDecimal saldoGlobal;
	private Cliente cliente;

	public Cuenta() {

	}

	public Cuenta(String id, BigDecimal saldo_global, Cliente cliente) {
		this.id = id;
		this.saldoGlobal = saldo_global;
		this.cliente = cliente;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getSaldoGlobal() {
		return saldoGlobal;
	}

	public void setSaldoGlobal(BigDecimal saldoGlobal) {
		this.saldoGlobal = saldoGlobal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public static Cuenta createCuenta() {
		return new Cuenta();
	}

	public Cuenta id(String id) {
		this.id = id;
		return this;
	}

	public Cuenta saldoGlobal(BigDecimal saldoGlobal) {
		this.saldoGlobal = saldoGlobal;
		return this;
	}

	public Cuenta cliente(Cliente cliente) {
		this.cliente = cliente;
		return this;
	}

	public Cuenta build() {
		return this;
	}

}
