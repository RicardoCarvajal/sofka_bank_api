package com.sofka.services.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
public class RabbitMqSender {

	@Autowired
	private Sender sender;

	@Autowired
	private Gson gson;

	public void senderError(Object object) {
		sender.send(Mono.just(new OutboundMessage(RabbitConf.EXCHANGE_NAME_1, RabbitConf.ROUTING_KEY_NAME_1,
				gson.toJson(object).getBytes()))).subscribe(System.out::println);
	}

}
