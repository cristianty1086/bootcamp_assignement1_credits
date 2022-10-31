package com.nttdata.bootcamp.assignement1.credits.infraestructure;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import com.nttdata.bootcamp.assignement1.credits.model.CreditPayment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditPaymentRepository extends ReactiveMongoRepository<CreditPayment, String> {

    Flux<CreditPayment> findByCreditId(String creditId);
}
