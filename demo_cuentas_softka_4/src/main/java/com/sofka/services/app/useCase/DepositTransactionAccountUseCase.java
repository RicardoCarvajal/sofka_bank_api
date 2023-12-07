package com.sofka.services.app.useCase;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

import com.sofka.services.app.config.RabbitMqSender;
import com.sofka.services.app.dto.AccountDto;
import com.sofka.services.app.dto.CustomerDto;
import com.sofka.services.app.dto.DepositDto;
import com.sofka.services.app.entity.Transaccion;
import com.sofka.services.app.repository.IAccountRepository;
import com.sofka.services.app.repository.ITransactionRepository;

import reactor.core.publisher.Mono;

public class DepositTransactionAccountUseCase implements IDepositTransaction {

	@Value("${app.deposito.account}")
	private String depositCommission;

	private final ITransactionRepository transactionRepository;

	private final IAccountRepository accountReporitory;

	private final RabbitMqSender sender;

	public DepositTransactionAccountUseCase(ITransactionRepository transactionRepository, IAccountRepository accountReporitory,
			RabbitMqSender sender) {
		this.transactionRepository = transactionRepository;
		this.accountReporitory = accountReporitory;
		this.sender = sender;
	}

	@Override
	public Mono<AccountDto> apply(DepositDto depositDto) {
		return accountReporitory.findByid(depositDto.getIdAccount()).flatMap(c -> {
			return transactionRepository.save(Transaccion.createTransaccion().cuenta(c)
					.montoTransaccion(depositDto.getAmount()).saldoInicial(c.getSaldoGlobal())
					.saldoFinal(
							c.getSaldoGlobal().add(depositDto.getAmount().subtract(new BigDecimal(depositCommission))))
					.tipo("ACCOUNT").costoTransaccion(new BigDecimal(depositCommission))
					.origen(depositDto.getIdOrigin()).build());
		}).flatMap(t -> {
			t.getCuenta().setSaldoGlobal(t.getSaldoFinal());
			return accountReporitory.save(t.getCuenta());
		}).map(c -> {
			return AccountDto.createAccountDto().id(c.getId()).globalBalance(c.getSaldoGlobal())
					.customer(
							CustomerDto.createCustomerDto().id(c.getCliente().getId()).name(c.getCliente().getNombre()))
					.build();
		}).onErrorResume(e -> {
			sender.senderError(depositDto);
			return accountReporitory.findByid(depositDto.getIdAccount()).flatMap(c -> {
				return Mono.just(AccountDto
						.createAccountDto().id(c.getId()).globalBalance(c.getSaldoGlobal()).customer(CustomerDto
								.createCustomerDto().id(c.getCliente().getId()).name(c.getCliente().getNombre()))
						.build());
			});
		});
	}

}
