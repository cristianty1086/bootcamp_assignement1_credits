package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface CreditService {
    // crear
    Mono<Credit> createCredit(Credit credit);
    // leer
    Mono<Credit> readCredit(String creditId);
    Flux<Credit> readCreditstByCostumer(BigInteger costumerId);
    // actualizar
    Mono<Credit> updateCredit(Credit credit);
    // delete
    Mono<Void> deleteCredit(String creditId);
    // leer todas
    Flux<Credit> listarTodos();
    Mono<Long> getCountCreditById(BigInteger costumerId);
    Mono<Long> getCountCreditCardsById(BigInteger costumerId);
}
