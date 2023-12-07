package com.sofka.services.app.useCase;

import org.springframework.stereotype.Service;

import com.sofka.services.app.dto.AccountDto;
import com.sofka.services.app.dto.CustomerDto;
import com.sofka.services.app.entity.Cliente;
import com.sofka.services.app.entity.Cuenta;
import com.sofka.services.app.repository.IAccountRepository;

import reactor.core.publisher.Mono;

@Service
public class CreateAccountUseCase {

	private final IAccountRepository accountRepository;

	public CreateAccountUseCase(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Mono<AccountDto> create(AccountDto accountDto) {
		return accountRepository.save(Cuenta.createCuenta().id(accountDto.getId())
				.saldoGlobal(accountDto.getGlobalBalance()).cliente(Cliente.createCliente()
						.id(accountDto.getCustomer().getId()).nombre(accountDto.getCustomer().getName()).build())
				.build()).map(c -> {

					return AccountDto
							.createAccountDto().id(c.getId()).globalBalance(c.getSaldoGlobal()).customer(CustomerDto
									.createCustomerDto().id(c.getCliente().getId()).name(c.getCliente().getNombre()))
							.build();

				});

	}

}
