package com.sofka.services.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sofka.services.app.entity.Transaccion;

public interface ITransactionRepository extends ReactiveMongoRepository<Transaccion, String> {

}
