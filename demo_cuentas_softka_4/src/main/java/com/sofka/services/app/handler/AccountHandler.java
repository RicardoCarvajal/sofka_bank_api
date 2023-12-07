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
import com.sofka.services.app.useCase.CreateAccountUseCase;
import com.sofka.services.app.useCase.GetAccountsUseCase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {

	private final CreateAccountUseCase createAccountUseCase;

	private final GetAccountsUseCase getAccountUseCase;

	private final Validator validator;

	public AccountHandler(CreateAccountUseCase createAccountUseCase, GetAccountsUseCase getAccountUseCase,
			Validator validator) {
		this.createAccountUseCase = createAccountUseCase;
		this.getAccountUseCase = getAccountUseCase;
		this.validator = validator;
	}

	public Mono<ServerResponse> listAccount(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(getAccountUseCase.get(),
				AccountDto.class);
	}

	public Mono<ServerResponse> createAccount(ServerRequest request) {
		Mono<AccountDto> account = request.bodyToMono(AccountDto.class);
		return account.flatMap(a -> {
			Errors errors = new BeanPropertyBindingResult(a, AccountDto.class.getName());
			validator.validate(a, errors);
			if (errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
						.collectList().flatMap(list -> ServerResponse.badRequest().bodyValue(list));
			} else {
				return createAccountUseCase.create(a)
						.flatMap(adb -> ServerResponse.created(URI.create("/api/v2/productos/"))
								.contentType(MediaType.APPLICATION_JSON).bodyValue(adb));
			}
		});

	}

}
