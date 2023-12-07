package com.sofka.services.app.dto;

import jakarta.validation.constraints.NotNull;

public class CustomerDto {

	@NotNull
	private String id;

	@NotNull
	private String name;

	public CustomerDto() {

	}

	public CustomerDto(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static CustomerDto createCustomerDto() {
		return new CustomerDto();
	}

	public CustomerDto id(String id) {
		this.id = id;
		return this;
	}

	public CustomerDto name(String name) {
		this.name = name;
		return this;
	}

	public CustomerDto build() {
		return this;
	}

}
