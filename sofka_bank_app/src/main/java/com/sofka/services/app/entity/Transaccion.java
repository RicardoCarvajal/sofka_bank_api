package com.sofka.services.app.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("transacciones")
public class Transaccion {
	private String id;
	private BigDecimal montoTransaccion;
	private BigDecimal saldoInicial;
	private BigDecimal saldoFinal;
	private BigDecimal costoTransaccion;
	private String tipo;
	private Cuenta cuenta;
	private String origen;

	public Transaccion() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getMontoTransaccion() {
		return montoTransaccion;
	}

	public void setMontoTransaccion(BigDecimal montoTransaccion) {
		this.montoTransaccion = montoTransaccion;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public BigDecimal getCostoTransaccion() {
		return costoTransaccion;
	}

	public void setCostoTransaccion(BigDecimal costoTransaccion) {
		this.costoTransaccion = costoTransaccion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public static Transaccion createTransaccion() {
		return new Transaccion();
	}

	public Transaccion id(String id) {
		this.id = id;
		return this;
	}

	public Transaccion montoTransaccion(BigDecimal montoTransaccion) {
		this.montoTransaccion = montoTransaccion;
		return this;
	}

	public Transaccion saldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
		return this;
	}

	public Transaccion saldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
		return this;
	}

	public Transaccion costoTransaccion(BigDecimal costoTransaccion) {
		this.costoTransaccion = costoTransaccion;
		return this;
	}

	public Transaccion tipo(String tipo) {
		this.tipo = tipo;
		return this;
	}

	public Transaccion cuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
		return this;
	}

	public Transaccion origen(String origen) {
		this.origen = origen;
		return this;
	}

	public Transaccion build() {
		return this;
	}

}
