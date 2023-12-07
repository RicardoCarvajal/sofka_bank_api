package com.sofka.services.app.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sofka.services.app.handler.TransactionHandler;

@Configuration
public class TransactionRouter {

	@Bean
	public RouterFunction<ServerResponse> getTransactions(TransactionHandler transactionHandler) {
		return route(GET("/api/transaction"), transactionHandler::getTransactions);
	}

	@Bean
	public RouterFunction<ServerResponse> depositAtm(TransactionHandler transactionHandler) {
		return route(POST("/api/transaction/deposit/atm"), transactionHandler::depositAtm);
	}

	@Bean
	public RouterFunction<ServerResponse> depositBank(TransactionHandler transactionHandler) {
		return route(POST("/api/transaction/deposit/bank"), transactionHandler::depositBank);
	}

	@Bean
	public RouterFunction<ServerResponse> depositAccount(TransactionHandler transactionHandler) {
		return route(POST("/api/transaction/deposit/account"), transactionHandler::depositAccount);
	}

}
