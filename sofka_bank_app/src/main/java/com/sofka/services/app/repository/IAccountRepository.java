package com.sofka.services.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sofka.services.app.entity.Cuenta;

import reactor.core.publisher.Mono;

public interface IAccountRepository extends ReactiveMongoRepository<Cuenta, String> {

	public Mono<Cuenta> findByid(String id);

	public Mono<Cuenta> save(Cuenta cuenta);

}
