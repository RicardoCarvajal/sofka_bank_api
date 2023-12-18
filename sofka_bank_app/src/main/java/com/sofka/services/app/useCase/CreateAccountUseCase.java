package com.sofka.services.app.useCase;

import org.springframework.stereotype.Service;

import com.sofka.services.app.entity.Cuenta;
import com.sofka.services.app.repository.IAccountRepository;

import reactor.core.publisher.Mono;

@Service
public class CreateAccountUseCase {

	private final IAccountRepository accountRepository;

	public CreateAccountUseCase(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Mono<Cuenta> create(Cuenta cuenta) {
		return accountRepository.save(cuenta);

	}

}
