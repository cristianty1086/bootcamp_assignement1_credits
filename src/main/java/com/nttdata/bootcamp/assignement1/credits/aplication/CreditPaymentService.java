package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import com.nttdata.bootcamp.assignement1.credits.model.CreditPayment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditPaymentService {
    // crear
    Mono<CreditPayment> createCreditPayment(CreditPayment creditPayment);
    // leer
    Mono<CreditPayment> readCreditPayment(String creditPaymentId);
    // actualizar
    Mono<CreditPayment> updateCreditPayment(CreditPayment creditPayment);
    // delete
    Mono<Void> deleteCreditPayment(String creditPaymentId);
    // leer todas
    Flux<CreditPayment> listarTodos();
}
