package com.nttdata.bootcamp.assignement1.credits.infraestructure;

import com.nttdata.bootcamp.assignement1.credits.model.CreditPayment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditPaymentRepository extends ReactiveMongoRepository<CreditPayment, String> {

}
