package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.infraestructure.CreditPaymentRepository;
import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import com.nttdata.bootcamp.assignement1.credits.model.CreditPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class CreditPaymentServiceImpl implements CreditPaymentService{
    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditPaymentServiceImpl.class);

    @Autowired
    CreditPaymentRepository creditPaymentRepository;

    @Autowired
    CreditService creditService;

    @Override
    public Mono<CreditPayment> createCreditPayment(Mono<CreditPayment> creditPayment) {
        LOGGER.info("Solicitud realizada para crear creditPayment");
        if( creditPayment == null ) {
            return null;
        }
        Optional<CreditPayment> creditPayment1 = creditPayment.blockOptional();
        if( !creditPayment1.isPresent() ) {
            LOGGER.error("Error, datos enviados incompletos");
            return null;
        }

        CreditPayment _creditPayment1 = creditPayment1.get();
        Mono<Credit> credit = creditService.readCredit(_creditPayment1.getCreditId());
        if( !credit.blockOptional().isPresent() ) {
            LOGGER.error("Error, datos enviados incompletos");
            return null;
        }
        Credit credit1 = credit.blockOptional().get();
        if( credit1.getCurrentBalance() + _creditPayment1.getAmount() > credit1.getLimitAmount() ) {
            LOGGER.error("Error, el movimiento supera el maximo permitido para la tarjeta");
            return null;
        }

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
        return creditPaymentRepository.save(creditPayment);
    }

    @Override
    public Mono<Void> deleteCreditPayment(Integer creditPaymentId) {
        LOGGER.info("Solicitud realizada para crear CreditPayment");
        return creditPaymentRepository.deleteById(creditPaymentId);
    }

    @Override
    public Flux<CreditPayment> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los CreditPayment");
        return creditPaymentRepository.findAll();
    }
}
