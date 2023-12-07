package com.sofka.services.app.useCase;

import com.sofka.services.app.dto.AccountDto;
import com.sofka.services.app.dto.DepositDto;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface IDepositTransaction {

	Mono<AccountDto> apply(DepositDto depositDto);

}
