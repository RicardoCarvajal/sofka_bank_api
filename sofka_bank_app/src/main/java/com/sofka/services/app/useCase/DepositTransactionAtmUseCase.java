package com.sofka.services.app.useCase;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sofka.services.app.dto.AccountDto;
import com.sofka.services.app.dto.CustomerDto;
import com.sofka.services.app.dto.DepositDto;
import com.sofka.services.app.entity.Transaccion;
import com.sofka.services.app.queue.RabbitMqSender;
import com.sofka.services.app.repository.IAccountRepository;
import com.sofka.services.app.repository.ITransactionRepository;
import com.sofka.services.app.util.MonitoredProcesses;

import reactor.core.publisher.Mono;

@Service
public class DepositTransactionAtmUseCase implements IDepositTransaction {

	@Value("${app.deposito.atm}")
	private String depositCommission;

	private final MonitoredProcesses monitoredProcesses;

	private final ITransactionRepository transactionRepository;

	private final IAccountRepository accountReporitory;

	private final RabbitMqSender sender;

	public DepositTransactionAtmUseCase(MonitoredProcesses monitoredProcesses,
			ITransactionRepository transactionRepository, IAccountRepository accountReporitory, RabbitMqSender sender) {
		this.monitoredProcesses = monitoredProcesses;
		this.transactionRepository = transactionRepository;
		this.accountReporitory = accountReporitory;
		this.sender = sender;
	}

	@Override
	public Mono<AccountDto> apply(DepositDto depositDto) {

		monitoredProcesses.info("Actualizando cuenta  " + depositDto.getIdAccount() + " por ATM");

		return accountReporitory.findByid(depositDto.getIdAccount()).flatMap(c -> {

			return transactionRepository.save(Transaccion.createTransaccion().cuenta(c)
					.montoTransaccion(depositDto.getAmount()).saldoInicial(c.getSaldoGlobal())
					.saldoFinal(
							c.getSaldoGlobal().add(depositDto.getAmount().subtract(new BigDecimal(depositCommission))))
					.tipo("ATM").costoTransaccion(new BigDecimal(depositCommission)).origen(depositDto.getIdOrigin())
					.build());
		}).flatMap(t -> {

			monitoredProcesses.info("Transaccion " + t.getId() + " registrada", t);

			depositDto.setIdOrigin(t.getId());

			t.getCuenta().setSaldoGlobal(t.getSaldoFinal());

			if (t.getCuenta().getCliente().getId().equalsIgnoreCase("V16772439"))
				return accountReporitory.save(null);/* FALLA */

			return accountReporitory.save(t.getCuenta());
		}).map(c -> {

			monitoredProcesses.info("Cuenta " + c.getId() + " actualizada", c);

			return AccountDto.createAccountDto().id(c.getId()).globalBalance(c.getSaldoGlobal())
					.customer(
							CustomerDto.createCustomerDto().id(c.getCliente().getId()).name(c.getCliente().getNombre()))
					.build();

		}).onErrorResume(e -> {
			monitoredProcesses.error("No se pudo actualizar la cuenta " + depositDto.getIdAccount()
					+ " para el deposito de " + depositDto.getAmount() + " $ ", depositDto);
			depositDto.setAmount(depositDto.getAmount().subtract(new BigDecimal(depositCommission)).negate());
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
