package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import com.nttdata.bootcamp.assignement1.credits.model.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {
    // crear
    Mono<CreditCard> createCreditCard(Mono<CreditCard> creditCard);
    // leer
    Mono<CreditCard> readCreditCard(Integer creditCardId);
    // actualizar
    Mono<CreditCard> updateCreditCard(CreditCard creditCard);
    // delete
    Mono<Void> deleteCreditCard(Integer creditCardId);
    // leer todas
    Flux<CreditCard> listarTodos();
}
