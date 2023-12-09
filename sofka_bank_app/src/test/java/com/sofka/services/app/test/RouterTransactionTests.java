package com.sofka.services.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.sofka.services.app.dto.TransactionDto;
import com.sofka.services.app.useCase.GetTransactionsUseCase;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterTransactionTests {

	@Autowired
	private WebTestClient client;

	@MockBean
	private GetTransactionsUseCase getTransactionsUseCase;

	@Test
	void getTransactionsTest() {

		TransactionDto transaction1 = TransactionDto.createTransactionDto().id("65839fa84c08dd574e081bdd")
				.account("65739fa84c08dd775e081bdd").amountTransaction(new BigDecimal(30))
				.initBalence(new BigDecimal(100)).finalBalance(new BigDecimal(128)).transactionCost(new BigDecimal(2))
				.type("ATM");

		TransactionDto transaction2 = TransactionDto.createTransactionDto().id("96539fa84c08dd574e081bdd")
				.account("85639fa84c08zz543e081bdd").amountTransaction(new BigDecimal(30))
				.initBalence(new BigDecimal(100)).finalBalance(new BigDecimal(128)).transactionCost(new BigDecimal(2))
				.type("ATM");

		List<TransactionDto> list = new ArrayList<TransactionDto>();

		list.add(transaction1);
		list.add(transaction2);

		when(getTransactionsUseCase.get()).thenReturn(Flux.fromIterable(list));

		client.get().uri("/api/transaction").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectBodyList(TransactionDto.class).consumeWith(response -> {
					List<TransactionDto> listResponse = response.getResponseBody();

					listResponse.forEach(t -> {
						System.out.println(t.getId());
						assertEquals(t.getType(), "ATM");
					});

				});
	}

}
