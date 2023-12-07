package com.sofka.services.app.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sofka.services.app.handler.AccountHandler;

@Configuration
public class AccountRouter {

	@Bean
	public RouterFunction<ServerResponse> listAccount(AccountHandler accountHandler) {
		return route(GET("/api/accoutn"), accountHandler::listAccount);
	}

	@Bean
	public RouterFunction<ServerResponse> createAccount(AccountHandler accountHandler) {
		return route(POST("/api/accoutn"), accountHandler::createAccount);
	}

}
