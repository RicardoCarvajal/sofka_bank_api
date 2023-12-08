package com.sofka.services.app.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sofka.services.app.config.RabbitConf;

import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
public class RabbitMqSender {

	Logger log = LoggerFactory.getLogger(RabbitMqSender.class);

	@Autowired
	private Sender sender;

	@Autowired
	private Gson gson;

	public void senderError(Object object) {
		log.info("Enviando data a cola de error " + gson.toJson(object));
		sender.send(Mono.just(new OutboundMessage(RabbitConf.EXCHANGE_NAME_1, RabbitConf.ROUTING_KEY_NAME_1,
				gson.toJson(object).getBytes()))).subscribe(System.out::println);
	}

	public void senderLogs(Object object) {
		log.info("Enviando data a cola de error " + gson.toJson(object));
		sender.send(Mono.just(new OutboundMessage(RabbitConf.EXCHANGE_NAME_2, RabbitConf.ROUTING_KEY_NAME_2,
				gson.toJson(object).getBytes()))).subscribe(System.out::println);
	}

}
