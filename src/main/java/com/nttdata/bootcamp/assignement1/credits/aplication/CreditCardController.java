package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("credit_card")
public class CreditCardController {

    @Autowired
    CreditCardService creditCardService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditCard> createCredit(@RequestBody CreditCard creditCard){
        System.out.println(creditCard);
        return creditCardService.createCreditCard(Mono.just(creditCard));
    }

    @GetMapping(value = "get/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<CreditCard> getCreditById(@PathVariable("id") String id){
        return creditCardService.readCreditCard(id);
    }

    @PutMapping(value = "update")
    @ResponseBody
    public Mono<CreditCard> updateCredit(@RequestBody CreditCard creditCard){
        return creditCardService.updateCreditCard(creditCard);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteCreditById(@PathVariable("id") String id){
        return creditCardService.deleteCreditCard(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<CreditCard> listarTodos(){
        return creditCardService.listarTodos();
    }
}
