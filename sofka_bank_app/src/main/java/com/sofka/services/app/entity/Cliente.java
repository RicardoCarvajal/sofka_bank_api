package com.sofka.services.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("clientes")
public class Cliente {

	@Id
	private String id;
	private String nombre;

	public Cliente() {

	}

	public Cliente(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static Cliente createCliente() {
		return new Cliente();
	}

	public Cliente id(String id) {
		this.id = id;
		return this;
	}

	public Cliente nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public Cliente build() {
		return this;
	}

}
