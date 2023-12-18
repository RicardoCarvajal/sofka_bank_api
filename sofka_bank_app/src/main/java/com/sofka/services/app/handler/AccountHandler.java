package com.sofka.services.app.handler;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sofka.services.app.dto.AccountDto;
import com.sofka.services.app.entity.Cliente;
import com.sofka.services.app.entity.Cuenta;
import com.sofka.services.app.useCase.CreateAccountUseCase;
import com.sofka.services.app.useCase.GetAccountsUseCase;
import com.sofka.services.app.util.MonitoredProcesses;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {

	private final MonitoredProcesses monitoredProcesses;

	private final CreateAccountUseCase createAccountUseCase;

	private final GetAccountsUseCase getAccountUseCase;

	private final Validator validator;

	public AccountHandler(MonitoredProcesses monitoredProcesses, CreateAccountUseCase createAccountUseCase,
			GetAccountsUseCase getAccountUseCase, Validator validator) {
		this.monitoredProcesses = monitoredProcesses;
		this.createAccountUseCase = createAccountUseCase;
		this.getAccountUseCase = getAccountUseCase;
		this.validator = validator;
	}

	public Mono<ServerResponse> listAccount(ServerRequest request) {
		monitoredProcesses.info("Solicitud para obtener todas las cuentas bancaria");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(getAccountUseCase.get(),
				AccountDto.class);
	}

	public Mono<ServerResponse> createAccount(ServerRequest request) {

		monitoredProcesses.info("Solicitud para creacion de cuenta bancaria");

		Mono<AccountDto> account = request.bodyToMono(AccountDto.class);
		return account.flatMap(a -> {
			Errors errors = new BeanPropertyBindingResult(a, AccountDto.class.getName());
			validator.validate(a, errors);
			if (errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
						.collectList().flatMap(list -> {
							monitoredProcesses.error("La peticion no fue creada correctamente");
							return ServerResponse.badRequest().bodyValue(list);
						});
			} else {
				return createAccountUseCase
						.create(Cuenta.createCuenta().id(a.getId()).saldoGlobal(a.getGlobalBalance()).cliente(Cliente
								.createCliente().id(a.getCustomer().getId()).nombre(a.getCustomer().getName()).build())
								.build())
						.flatMap(adb -> {
							monitoredProcesses.info("Cuenta bancaria creada", adb);

							return ServerResponse.created(URI.create("/api/v2/productos/"))
									.contentType(MediaType.APPLICATION_JSON).bodyValue(adb);
						});
			}
		});

	}

}
