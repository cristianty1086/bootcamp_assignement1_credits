package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.infraestructure.CreditRepository;
import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import com.nttdata.bootcamp.assignement1.credits.model.CreditCard;
import com.nttdata.bootcamp.assignement1.credits.model.CreditType;
import com.nttdata.bootcamp.assignement1.credits.utilities.BuilderUrl;
import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {
    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<Credit> createCredit(Credit credit) {
        LOGGER.info("Solicitud realizada para crear credit");
        if( credit == null ) {
            LOGGER.error("Error: datos insuficientes");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: datos insuficientes.", null);
        }

        if( credit.getCostumerId() == null ) {
            LOGGER.error("Error: datos insuficientes");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: datos insuficientes.", null);
        }

        if( credit.getCreditType() == null ) {
            LOGGER.error("Error: no indico el tipo de credito.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: no indico el tipo de credito.", null);
        }

        // obtener el numero de creditos de un cliente
        String url = BuilderUrl.buildCountCreditsByCostumerId(credit.getCostumerId());
        Long count = restTemplate.getForObject(url, Long.class);
        if( count == null ) {
            LOGGER.error("Error: al obtener el numero de creditos del cliente. Contactese con el soporte tecnico.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: al obtener el numero de creditos del cliente. Contactese con el soporte tecnico.", null);
        }

        // generar tarjeta de credito asociado
        if( credit.getCreditType() == CreditType.staff) {
            if( credit.getCostumerType().equals("person") ) {
                if( count > 0) {
                    LOGGER.error("Error: solo se permite un solo credito por persona.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: solo se permite un solo credito por persona.", null);
                }
            } else {
                LOGGER.error("Error: no aplica al tipo de cliente");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: no aplica al tipo de cliente", null);
            }
        } else if( credit.getCreditType() == CreditType.business) {
            if( credit.getCostumerType().equals("enterprise") ) {
            } else {
                LOGGER.error("Error: no aplica al tipo de cliente empresarial");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: no aplica al tipo de cliente empresarial", null);
            }
        } else if( credit.getCreditType() == CreditType.credit_card ) {

            // crear tarjeta de credito
            StringBuilder ss2 = new StringBuilder();
            ss2.append( (int)(1000 + (Math.random() * (9999 - 1000))) );
            ss2.append( " " );
            ss2.append( (int)(1000 + (Math.random() * (9999 - 1000))) );
            ss2.append( " " );
            ss2.append( (int)(1000 + (Math.random() * (9999 - 1000))) );
            ss2.append( " " );
            ss2.append( (int)(1000 + (Math.random() * (9999 - 1000))) );
            String _cvv = String.valueOf( (int)( 100 + (Math.random() * (999 - 100))));
            String _year = String.valueOf((int)(2022 + (Math.random() * (2035 - 2022))));
            String _month = String.valueOf((int)(1 + (Math.random() * (12 - 1))));

            CreditCard creditCard = new CreditCard();
            creditCard.setCcv( _cvv );
            creditCard.setCardNumber( ss2.toString() );
            creditCard.setExpireYear( _year );
            creditCard.setExpireMonth( _month );

            List<CreditCard> creditCards = new ArrayList<>();
            creditCards.add(creditCard);
            credit.setCreditCard(creditCards);
        }

        // buscar entre las cuentas, cuantos tienen deuda pendiente
        String url5 = BuilderUrl.buildGetCreditsByCostumerId(credit.getCostumerId());
        String strBankAccount = restTemplate.getForObject(url5, String.class);
        JSONArray jsonArray = new JSONArray(strBankAccount);
        int[] cuentas_pendientes = {0};
        jsonArray.forEach(it -> {
            JSONObject jsonObject = (JSONObject) it;
            String nextDatePayment = jsonObject.getString("nextDatePayment");
            if( nextDatePayment == null || nextDatePayment.isEmpty() ) {
                LOGGER.warn("Sin informacion de fecha de pago siguiente");
            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDateTime d1 = LocalDateTime.parse(nextDatePayment, dtf);
                LocalDateTime d2 = LocalDateTime.now();
                if(d1.isBefore(d2)) {
                    LOGGER.info("Tiene una deuda pendiente");
                    cuentas_pendientes[0] += 1;
                }
            }
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        credit.setCreatedAt(LocalDateTime.now().format(dtf));

        if( cuentas_pendientes[0] > 0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tiene un credito pendiente de pago, no podra crear una nueva cuenta bancaria", null);
        }

        return creditRepository.save(credit);
    }

    @Override
    public Mono<Credit> readCredit(String creditId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un cliente");
        return creditRepository.findById(creditId);
    }

    @Override
    public Flux<Credit> readCreditstByCostumer(BigInteger costumerId) {
        LOGGER.info("Solicitud realizada para obtener las cuentas bancarias de un cliente");
        return creditRepository.findByCostumerId(costumerId);
    }

    @Override
    public Mono<Credit> updateCredit(Credit credit) {
        LOGGER.info("Solicitud realizada para actualizar al credito");
        return creditRepository.save(credit);
    }

    @Override
    public Mono<Void> deleteCredit(String creditId) {
        LOGGER.info("Solicitud realizada para crear cliente");
        creditRepository.deleteById(creditId);
        return null;
    }

    @Override
    public Flux<Credit> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los clientes");
        return creditRepository.findAll();
    }

    @Override
    public Mono<Long> getCountCreditById(BigInteger costumerId){
        return creditRepository.countByCostumerId(costumerId);
    }

    @Override
    public Mono<Long> getCountCreditCardsById(BigInteger costumerId){
        return creditRepository.countByCostumerIdAndCreditType(costumerId,CreditType.credit_card);
    }

    @Override
    public Flux<Credit> listarTodosBeetween(String dateInit, String dateEnd) {
        LOGGER.info("Solicitud realizada para el envio de todos los BankAccount en un periodo determinado");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return creditRepository.findAll().filter(a -> {
            if( dateInit != null && dateEnd != null ) {
                LocalDateTime d1 = LocalDateTime.parse(a.getCreatedAt(), dtf);
                LocalDateTime d2 = LocalDateTime.parse(dateInit, dtf);
                LocalDateTime d3 = LocalDateTime.parse(dateEnd, dtf);
                return (d1.isAfter(d2) && d1.isBefore(d3));
            } else if(dateInit != null) {
                LocalDateTime d1 = LocalDateTime.parse(a.getCreatedAt(), dtf);
                LocalDateTime d2 = LocalDateTime.parse(dateInit, dtf);
                return d1.isAfter(d2);
            } else if(dateEnd != null) {
                LocalDateTime d1 = LocalDateTime.parse(a.getCreatedAt(), dtf);
                LocalDateTime d3 = LocalDateTime.parse(dateEnd, dtf);
                return d1.isBefore(d3);
            } else {
                return false;
            }
        });
    }

}
