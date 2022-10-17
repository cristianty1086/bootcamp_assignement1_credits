package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.infraestructure.CreditCardRepository;
import com.nttdata.bootcamp.assignement1.credits.model.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditCardServiceImpl implements CreditCardService{
    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    CreditCardRepository creditCardRepository;

    @Override
    public Mono<CreditCard> createCreditCard(Mono<CreditCard> creditCard) {
        LOGGER.info("Solicitud realizada para crear creditCard");
        return creditCard.flatMap(creditCardRepository::insert);
    }

    @Override
    public Mono<CreditCard> readCreditCard(Integer creditCardId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un CreditCard");
        return creditCardRepository.findById(creditCardId);
    }

    @Override
    public Mono<CreditCard> updateCreditCard(CreditCard creditCard) {
        LOGGER.info("Solicitud realizada para actualizar al CreditCard");
        creditCardRepository.findById(creditCard.getId())
                .map( currCostumer -> {
                    LOGGER.info("Cliente encontrado para el id: " + creditCard.getId());
                    currCostumer.setHolderName(creditCard.getHolderName());
                    currCostumer.setCardNumber(creditCard.getCardNumber());
                    currCostumer.setExpireMonth(creditCard.getExpireMonth());
                    currCostumer.setExpireYear(creditCard.getExpireYear());
                    currCostumer.setCcv(creditCard.getCcv());
                    return creditCardRepository.save(currCostumer);
                });

        return Mono.just(creditCard);
    }

    @Override
    public Mono<Void> deleteCreditCard(Integer creditCardId) {
        LOGGER.info("Solicitud realizada para crear CreditCard");
        creditCardRepository.deleteById(creditCardId);
        return null;
    }

    @Override
    public Flux<CreditCard> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los CreditCard");
        return creditCardRepository.findAll();
    }
}
