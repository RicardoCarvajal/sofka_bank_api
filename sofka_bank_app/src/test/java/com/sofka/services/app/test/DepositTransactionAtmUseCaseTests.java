package com.sofka.services.app.test;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sofka.services.app.dto.DepositDto;
import com.sofka.services.app.dto.TransactionDto;
import com.sofka.services.app.entity.Cliente;
import com.sofka.services.app.entity.Cuenta;
import com.sofka.services.app.entity.Transaccion;
import com.sofka.services.app.repository.IAccountRepository;
import com.sofka.services.app.repository.ITransactionRepository;
import com.sofka.services.app.useCase.DepositTransactionAtmUseCase;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepositTransactionAtmUseCaseTests {

	@MockBean
	private ITransactionRepository transactionRepository;

	@MockBean
	private IAccountRepository accountReporitory;

	@Autowired
	private DepositTransactionAtmUseCase depositTransactionAtmUseCase;

	@Test
	void depositTransaction() {

		TransactionDto transactionDto = TransactionDto.createTransactionDto().id("65839fa84c08dd574e081bdd")
				.account("65739fa84c08dd775e081bdd").amountTransaction(new BigDecimal(30))
				.initBalence(new BigDecimal(100)).finalBalance(new BigDecimal(128)).transactionCost(new BigDecimal(2))
				.type("ATM").build();

		Cuenta cuenta = Cuenta.createCuenta()
				.cliente(Cliente.createCliente().id("V16772458").nombre("Ricardo Carvajal").build())
				.id("65739fa84c08dd775e081bdd").saldoGlobal(new BigDecimal(200)).build();

		Transaccion transaccion = Transaccion.createTransaccion().id("65839fa84c08dd574e081bdd")
				.costoTransaccion(new BigDecimal(2)).cuenta(cuenta).montoTransaccion(new BigDecimal(30))
				.saldoInicial(new BigDecimal(100)).saldoFinal(new BigDecimal(128)).tipo("ATM").origen("ATM00582")
				.build();

		DepositDto deposit = DepositDto.createDepositDto().amount(new BigDecimal(30))
				.idAccount("65739fa84c08dd775e081bdd").idOrigin("ATM00582").build();

		Mockito.when(transactionRepository.save(transaccion)).thenReturn(Mono.just(transaccion));

		Mockito.when(accountReporitory.save(cuenta)).thenReturn(Mono.just(cuenta));

		Mockito.when(accountReporitory.findById("65739fa84c08dd775e081bdd")).thenReturn(Mono.just(cuenta));

		var result = depositTransactionAtmUseCase.apply(deposit);

	}

}
