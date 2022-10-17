package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
    // crear
    Mono<Credit> createCredit(Mono<Credit> credit);
    // leer
    Mono<Credit> readCredit(Integer creditId);
    // actualizar
    Mono<Credit> updateCredit(Credit credit);
    // delete
    Mono<Void> deleteCredit(Integer creditId);
    // leer todas
    Flux<Credit> listarTodos();
}
