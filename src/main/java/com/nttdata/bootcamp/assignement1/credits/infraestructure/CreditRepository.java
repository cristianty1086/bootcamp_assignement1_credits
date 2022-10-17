package com.nttdata.bootcamp.assignement1.credits.infraestructure;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, Integer> {

}
