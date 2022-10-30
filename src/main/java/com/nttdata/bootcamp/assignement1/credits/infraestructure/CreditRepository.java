package com.nttdata.bootcamp.assignement1.credits.infraestructure;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import com.nttdata.bootcamp.assignement1.credits.model.CreditType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

    Mono<Long> countByCostumerId(BigInteger costumerId);
    Flux<Credit> findByCostumerId(BigInteger costumerId);
    Mono<Long> countByCostumerIdAndCreditType(BigInteger costumerId, CreditType creditType);
}
