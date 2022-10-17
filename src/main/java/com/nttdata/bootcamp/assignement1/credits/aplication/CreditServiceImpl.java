package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.infraestructure.CreditRepository;
import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);

    @Autowired
    CreditRepository costumerRepository;

    @Override
    public Mono<Credit> createCredit(Mono<Credit> credit) {
        LOGGER.info("Solicitud realizada para crear cliente");
        return credit.flatMap(costumerRepository::insert);
    }

    @Override
    public Mono<Credit> readCredit(Integer creditId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un cliente");
        return costumerRepository.findById(creditId);
    }

    @Override
    public Mono<Credit> updateCredit(Credit credit) {
        LOGGER.info("Solicitud realizada para actualizar al cliente");
        costumerRepository.findById(credit.getId())
                .map( currCostumer -> {
                    LOGGER.info("Cliente encontrado para el id: " + credit.getId());
                    currCostumer.setLimitAmount(credit.getLimitAmount());
                    currCostumer.setNextDatePayment(credit.getNextDatePayment());
                    currCostumer.setNextAmountPayment(credit.getNextAmountPayment());
                    currCostumer.setCurrentBalance(credit.getCurrentBalance());
                    return costumerRepository.save(currCostumer);
                });

        return Mono.just(credit);
    }

    @Override
    public Mono<Void> deleteCredit(Integer creditId) {
        LOGGER.info("Solicitud realizada para crear cliente");
        costumerRepository.deleteById(creditId);
        return null;
    }

    @Override
    public Flux<Credit> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los clientes");
        return costumerRepository.findAll();
    }
}
