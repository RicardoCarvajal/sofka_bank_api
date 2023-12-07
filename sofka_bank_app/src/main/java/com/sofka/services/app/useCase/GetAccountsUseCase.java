package com.sofka.services.app.useCase;

import org.springframework.stereotype.Service;

import com.sofka.services.app.dto.AccountDto;
import com.sofka.services.app.dto.CustomerDto;
import com.sofka.services.app.repository.IAccountRepository;

import reactor.core.publisher.Flux;

@Service
public class GetAccountsUseCase {

	private final IAccountRepository accountRepository;

	public GetAccountsUseCase(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Flux<AccountDto> get() {
		return accountRepository.findAll().map(c -> {
			return AccountDto
					.createAccountDto().id(c.getId()).globalBalance(c.getSaldoGlobal()).customer(CustomerDto
							.createCustomerDto().id(c.getCliente().getId()).name(c.getCliente().getNombre()).build())
					.build();
		});

	}

}
