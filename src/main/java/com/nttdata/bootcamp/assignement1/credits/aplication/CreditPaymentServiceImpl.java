package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.infraestructure.CreditPaymentRepository;
import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import com.nttdata.bootcamp.assignement1.credits.model.CreditPayment;
import com.nttdata.bootcamp.assignement1.credits.model.CreditPaymentType;
import com.nttdata.bootcamp.assignement1.credits.utilities.BuilderUrl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class CreditPaymentServiceImpl implements CreditPaymentService{
    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditPaymentServiceImpl.class);

    @Autowired
    CreditPaymentRepository creditPaymentRepository;

    @Override
    public Mono<CreditPayment> createCreditPayment(CreditPayment creditPayment) {
        LOGGER.info("Solicitud realizada para crear creditPayment");
        if( creditPayment == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos insuficientes", null);
        }
        if( creditPayment.getCreditId() == null || creditPayment.getCreditId().isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta: indidcar creditId", null);
        }
        if( creditPayment.getCreditPaymentType() == null ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Envie el tipo de pago de credito", null);
        }

        String url = BuilderUrl.buildGetCredit(creditPayment.getCreditId());
        RestTemplate restTemplate = new RestTemplate();
        Credit credit = restTemplate.getForObject(url, Credit.class);
        if( credit == null ) {
            LOGGER.error("Error: credito no encontrado");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: credito no encontrado", null);
        }
        if( credit.getCurrentBalance() + creditPayment.getAmount() > credit.getLimitAmount() ) {
            LOGGER.error("Error, el movimiento supera el maximo permitido para la tarjeta");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, el movimiento supera el maximo permitido para la tarjeta", null);
        }

        // actualizar el saldo
        double salida = 0.0;
        if (creditPayment.getCreditPaymentType() == CreditPaymentType.deposit) {
            salida = credit.getCurrentBalance() + creditPayment.getAmount();
        } else if (creditPayment.getCreditPaymentType() == CreditPaymentType.withdrawal) {
            salida = credit.getCurrentBalance() - creditPayment.getAmount();
            if(  salida < 0 ) {
                LOGGER.error("Error, el movimiento de retiro es mayor al disponible en su cuenta");
                return null;
            }
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        creditPayment.setRegisterDate(dtf.format(localDateTime));

        credit.setCurrentBalance( salida );
        String url2 = BuilderUrl.buildUpdateCredit();
        restTemplate.put(url2, credit);

        return creditPaymentRepository.save(creditPayment);
    }

    @Override
    public Mono<CreditPayment> readCreditPayment(String creditPaymentId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un CreditPayment");
        return creditPaymentRepository.findById(creditPaymentId);
    }

    @Override
    public Mono<CreditPayment> updateCreditPayment(CreditPayment creditPayment) {
        LOGGER.info("Solicitud realizada para actualizar al CreditPayment");
        return creditPaymentRepository.save(creditPayment);
    }

    @Override
    public Mono<Void> deleteCreditPayment(String creditPaymentId) {
        LOGGER.info("Solicitud realizada para crear CreditPayment");
        return creditPaymentRepository.deleteById(creditPaymentId);
    }

    @Override
    public Flux<CreditPayment> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los CreditPayment");
        return creditPaymentRepository.findAll();
    }

    @Override
    public Mono<CreditPayment> createCreditPaymentOfThird(CreditPayment creditPayment, String bankAccountId) {
        LOGGER.info("Solicitud realizada para crear creditPayment");
        if( creditPayment == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos insuficientes", null);
        }
        if( creditPayment.getCreditId() == null || creditPayment.getCreditId().isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta: indidcar creditId", null);
        }
        if( creditPayment.getCreditPaymentType() == null ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Envie el tipo de pago de credito", null);
        }

        String url = BuilderUrl.buildGetCredit(creditPayment.getCreditId());
        RestTemplate restTemplate = new RestTemplate();
        Credit credit = restTemplate.getForObject(url, Credit.class);
        if( credit == null ) {
            LOGGER.error("Error: credito no encontrado");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: credito no encontrado", null);
        }
        if( credit.getCurrentBalance() + creditPayment.getAmount() > credit.getLimitAmount() ) {
            LOGGER.error("Error, el movimiento supera el maximo permitido para la tarjeta");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, el movimiento supera el maximo permitido para la tarjeta", null);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        creditPayment.setRegisterDate(dtf.format(localDateTime));

        // actualizar el saldo del cliente tercero
        double salida = 0.0;
        if (creditPayment.getCreditPaymentType() == CreditPaymentType.deposit) {
            salida = credit.getCurrentBalance() + creditPayment.getAmount();
        } else if (creditPayment.getCreditPaymentType() == CreditPaymentType.withdrawal) {
            salida = credit.getCurrentBalance() - creditPayment.getAmount();
            if(  salida < 0 ) {
                LOGGER.error("Error, el movimiento de retiro es mayor al disponible en su cuenta");
                return null;
            }
        }
        credit.setCurrentBalance( salida );
        String url2 = BuilderUrl.buildUpdateCredit();
        restTemplate.put(url2, credit);

        // actualizar el saldo del cliente (usando cuenta bancaria)
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("movementType", "withdrawal");
        jsonObject.put("amount", creditPayment.getAmount());
        jsonObject.put("bankAccountId", bankAccountId);
        String url3 = BuilderUrl.buildCreateMovementInCostumerId();
        String resultado = restTemplate.postForObject(url3, jsonObject.toString(), String.class);
        LOGGER.info("Resultado "+resultado);

        return creditPaymentRepository.save(creditPayment);
    }

    @Override
    public Flux<CreditPayment> getLastTen(String creditCardId) {
        LOGGER.info("Solicitud realizada para el envio de los ultimos 10");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:MM:ss");
        return creditPaymentRepository.findByCreditId(creditCardId)
                .sort((a1,a2) -> {
                    LocalDateTime d1 = LocalDateTime.parse(a1.getRegisterDate(), dtf);
                    LocalDateTime d2 = LocalDateTime.parse(a2.getRegisterDate(), dtf);
                    return -1*d1.compareTo(d2);
                })
                .limitRate(10);
    }
}
