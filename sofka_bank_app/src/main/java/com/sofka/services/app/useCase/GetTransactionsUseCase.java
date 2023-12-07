package com.sofka.services.app.useCase;

import org.springframework.stereotype.Service;

import com.sofka.services.app.dto.TransactionDto;
import com.sofka.services.app.repository.ITransactionRepository;

import reactor.core.publisher.Flux;

@Service
public class GetTransactionsUseCase {

	private final ITransactionRepository transactionRepository;

	public GetTransactionsUseCase(ITransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Flux<TransactionDto> get() {
		return transactionRepository.findAll().map(transaccion -> {
			return TransactionDto.createTransactionDto().id(transaccion.getId())
					.account(transaccion.getCuenta().getId()).amountTransaction(transaccion.getMontoTransaccion())
					.initBalence(transaccion.getSaldoInicial()).finalBalance(transaccion.getSaldoFinal())
					.transactionCost(transaccion.getCostoTransaccion()).type(transaccion.getTipo()).build();
		});
	}

}
