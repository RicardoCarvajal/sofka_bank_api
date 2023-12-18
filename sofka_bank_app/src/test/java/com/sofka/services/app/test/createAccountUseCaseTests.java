package com.sofka.services.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sofka.services.app.entity.Cliente;
import com.sofka.services.app.entity.Cuenta;
import com.sofka.services.app.repository.IAccountRepository;
import com.sofka.services.app.useCase.CreateAccountUseCase;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class createAccountUseCaseTests {

	private CreateAccountUseCase createAccountUseCase;

	@Mock
	private IAccountRepository accountRepository;

	@BeforeEach
	void setUp() {
		createAccountUseCase = new CreateAccountUseCase(accountRepository);
	}

	@Test
	void create() {

		Cuenta cuentaRequest = Cuenta.createCuenta()
				.cliente(Cliente.createCliente().id("V16772437").nombre("Ricardo Carvajal").build())
				.id("65733dd0ec6ef0400a30e52c").saldoGlobal(new BigDecimal(50000)).build();

		Cuenta cuentaResponse = Cuenta.createCuenta()
				.cliente(Cliente.createCliente().id("V16772437").nombre("Ricardo Carvajal").build())
				.id("65733dd0ec6ef0400a30e52c").saldoGlobal(new BigDecimal(50000)).build();

		when(accountRepository.save(cuentaRequest)).thenReturn(Mono.just(cuentaResponse));

		var result = createAccountUseCase.create(cuentaRequest);

		StepVerifier.create(result).assertNext(c -> {
			assertEquals(c.getSaldoGlobal(), new BigDecimal(40000));
		}).verifyComplete();

		Mockito.verify(accountRepository).save(cuentaRequest);
	}

}
