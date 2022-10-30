package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RestController
@RequestMapping("credit")
public class CreditController {

    @Autowired
    CreditService creditService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Credit> createCredit(@RequestBody Credit credit){
        System.out.println(credit);
        return creditService.createCredit(credit);
    }

    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Credit> getCreditById(@PathVariable("id") String id){
        return creditService.readCredit(id);
    }

    @GetMapping(value = "get_by_costumer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<Credit> getCostumerId(@PathVariable("id") BigInteger id){
        return creditService.readCreditstByCostumer(id);
    }
    @PutMapping(value = "update")
    @ResponseBody
    public Mono<Credit> updateCredit(@RequestBody Credit credit){
        return creditService.updateCredit(credit);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteCreditById(@PathVariable("id") String id){
        return creditService.deleteCredit(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<Credit> listarTodos(){
        return creditService.listarTodos();
    }

    @GetMapping(value = "count_credits/{costumerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Long> getCountCreditById(@PathVariable("costumerId") BigInteger id){
        return creditService.getCountCreditById(id);
    }

    @GetMapping(value = "count_credits_cards/{costumerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Long> getCountCreditCardsById(@PathVariable("costumerId") BigInteger id){
        return creditService.getCountCreditCardsById(id);
    }

}
