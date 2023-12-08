package com.sofka.services.app.queue;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sofka.services.app.config.RabbitConf;
import com.sofka.services.app.dto.DepositDto;
import com.sofka.services.app.entity.Transaccion;
import com.sofka.services.app.repository.IAccountRepository;
import com.sofka.services.app.repository.ITransactionRepository;

import reactor.rabbitmq.Receiver;

@Component
public class RabbitMqReceiver implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(RabbitMqReceiver.class);

	@Autowired
	private Receiver receiver;

	@Autowired
	private Gson gson;

	private final ITransactionRepository transactionRepository;
	private final IAccountRepository accountReporitory;

	public RabbitMqReceiver(ITransactionRepository transactionRepository, IAccountRepository accountReporitory) {
		this.transactionRepository = transactionRepository;
		this.accountReporitory = accountReporitory;

	}

	@Override
	public void run(String... args) throws Exception {

		receiver.consumeAutoAck(RabbitConf.QUEUE_ERROR).flatMap(message -> {

			DepositDto d = gson.fromJson(new String(message.getBody()), DepositDto.class);
			log.info("Consumiendo: " + new String(message.getBody()) + " de cola 1");

			return accountReporitory.findByid(d.getIdAccount()).map(c -> {

				return Transaccion.createTransaccion().cuenta(c).montoTransaccion(d.getAmount())
						.saldoInicial(c.getSaldoGlobal()).saldoFinal(c.getSaldoGlobal())
						.costoTransaccion(BigDecimal.ZERO).tipo("REVERSO").origen(d.getIdOrigin());

			});
		}).flatMap(t -> {
			return transactionRepository.save(t);
		}).subscribe();

	}
}
