package com.sofka.services.app.handler;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sofka.services.app.dto.DepositDto;
import com.sofka.services.app.dto.TransactionDto;
import com.sofka.services.app.useCase.DepositTransactionAccountUseCase;
import com.sofka.services.app.useCase.DepositTransactionAtmUseCase;
import com.sofka.services.app.useCase.DepositTransactionBankUseCase;
import com.sofka.services.app.useCase.GetTransactionsUseCase;
import com.sofka.services.app.util.MonitoredProcesses;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {

	private final MonitoredProcesses monitoredProcesses;

	private final GetTransactionsUseCase getTransactionsUseCase;

	private final DepositTransactionAtmUseCase depositTransactionAtm;
	private final DepositTransactionAccountUseCase depositTransactionAccount;
	private final DepositTransactionBankUseCase depositTransactionBank;

	private final Validator validator;

	public TransactionHandler(MonitoredProcesses monitoredProcesses, GetTransactionsUseCase getTransactionsUseCase,
			DepositTransactionAtmUseCase depositTransactionAtm,
			DepositTransactionAccountUseCase depositTransactionAccount,
			DepositTransactionBankUseCase depositTransactionBank, Validator validator) {
		this.monitoredProcesses = monitoredProcesses;
		this.getTransactionsUseCase = getTransactionsUseCase;
		this.depositTransactionAtm = depositTransactionAtm;
		this.depositTransactionAccount = depositTransactionAccount;
		this.depositTransactionBank = depositTransactionBank;
		this.validator = validator;
	}

	public Mono<ServerResponse> getTransactions(ServerRequest request) {
		monitoredProcesses.info("Solicitud para obtener todas las cuentas transacciones");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(getTransactionsUseCase.get(),
				TransactionDto.class);
	}

	public Mono<ServerResponse> depositAtm(ServerRequest request) {
		monitoredProcesses.info("Solicitud para generar un deposito");
		Mono<DepositDto> deposit = request.bodyToMono(DepositDto.class);

		return deposit.flatMap(d -> {

			Errors errors = new BeanPropertyBindingResult(d, DepositDto.class.getName());
			validator.validate(d, errors);
			if (errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
						.collectList().flatMap(list -> {
							monitoredProcesses.error("La peticion no fue creada correctamente");
							return ServerResponse.badRequest().bodyValue(list);
						});
			} else {
				return depositTransactionAtm.apply(d).flatMap(adb -> {

					monitoredProcesses.info("Transaccion creada, cuenta bancaria actualizada", adb);

					return ServerResponse.created(URI.create("/api/transaction"))
							.contentType(MediaType.APPLICATION_JSON).bodyValue(adb);
				});
			}
		});

	}

	public Mono<ServerResponse> depositBank(ServerRequest request) {
		Mono<DepositDto> deposit = request.bodyToMono(DepositDto.class);

		return deposit.flatMap(d -> {

			Errors errors = new BeanPropertyBindingResult(d, DepositDto.class.getName());
			validator.validate(d, errors);
			if (errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
						.collectList().flatMap(list -> {
							monitoredProcesses.error("La peticion no fue creada correctamente");
							return ServerResponse.badRequest().bodyValue(list);
						});
			} else {
				return depositTransactionBank.apply(d).flatMap(adb -> {

					monitoredProcesses.info("Transaccion creada, cuenta bancaria actualizada", adb);

					return ServerResponse.created(URI.create("/api/transaction"))
							.contentType(MediaType.APPLICATION_JSON).bodyValue(adb);
				});
			}
		});

	}

	public Mono<ServerResponse> depositAccount(ServerRequest request) {
		Mono<DepositDto> deposit = request.bodyToMono(DepositDto.class);

		return deposit.flatMap(d -> {

			Errors errors = new BeanPropertyBindingResult(d, DepositDto.class.getName());
			validator.validate(d, errors);
			if (errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
						.collectList().flatMap(list -> {
							monitoredProcesses.error("La peticion no fue creada correctamente");

							return ServerResponse.badRequest().bodyValue(list);
						});
			} else {
				return depositTransactionAccount.apply(d).flatMap(adb -> {

					monitoredProcesses.info("Transaccion creada, cuenta bancaria actualizada", adb);

					return ServerResponse.created(URI.create("/api/transaction"))
							.contentType(MediaType.APPLICATION_JSON).bodyValue(adb);
				});
			}
		});

	}

}
