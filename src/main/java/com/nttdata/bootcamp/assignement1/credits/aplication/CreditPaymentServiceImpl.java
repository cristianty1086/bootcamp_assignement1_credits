package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.infraestructure.CreditPaymentRepository;
import com.nttdata.bootcamp.assignement1.credits.model.CreditPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditPaymentServiceImpl implements CreditPaymentService{
    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditPaymentServiceImpl.class);

    @Autowired
    CreditPaymentRepository creditPaymentRepository;

    @Override
    public Mono<CreditPayment> createCreditPayment(Mono<CreditPayment> creditPayment) {
        LOGGER.info("Solicitud realizada para crear creditPayment");
        return creditPayment.flatMap(creditPaymentRepository::insert);
    }

    @Override
    public Mono<CreditPayment> readCreditPayment(Integer creditPaymentId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un CreditPayment");
        return creditPaymentRepository.findById(creditPaymentId);
    }

    @Override
    public Mono<CreditPayment> updateCreditPayment(CreditPayment creditPayment) {
        LOGGER.info("Solicitud realizada para actualizar al CreditPayment");
        creditPaymentRepository.findById(creditPayment.getId())
                .map( currCostumer -> {
                    LOGGER.info("Cliente encontrado para el id: " + creditPayment.getId());
                    currCostumer.setAmount(creditPayment.getAmount());
                    return creditPaymentRepository.save(currCostumer);
                });

        return Mono.just(creditPayment);
    }

    @Override
    public Mono<Void> deleteCreditPayment(Integer creditPaymentId) {
        LOGGER.info("Solicitud realizada para crear CreditPayment");
        creditPaymentRepository.deleteById(creditPaymentId);
        return null;
    }

    @Override
    public Flux<CreditPayment> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los CreditPayment");
        return creditPaymentRepository.findAll();
    }
}
