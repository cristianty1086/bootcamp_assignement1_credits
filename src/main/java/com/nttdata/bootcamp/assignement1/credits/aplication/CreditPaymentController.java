package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.CreditPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("credit_payment")
public class CreditPaymentController {

    @Autowired
    CreditPaymentService creditPaymentService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditPayment> createCredit(@RequestBody CreditPayment creditPayment){
        System.out.println(creditPayment);
        return creditPaymentService.createCreditPayment(creditPayment);
    }

    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<CreditPayment> getCreditById(@PathVariable("id") String id){
        return creditPaymentService.readCreditPayment(id);
    }

    @PutMapping(value = "update")
    @ResponseBody
    public Mono<CreditPayment> updateCredit(@RequestBody CreditPayment Credit){
        return creditPaymentService.updateCreditPayment(Credit);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteCreditById(@PathVariable("id") String id){
        return creditPaymentService.deleteCreditPayment(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<CreditPayment> listarTodos(){
        return creditPaymentService.listarTodos();
    }

    @PostMapping("create_from_third/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditPayment> createCredit(@RequestBody CreditPayment creditPayment, @PathVariable("id") String bankAccountId){
        System.out.println(creditPayment);
        return creditPaymentService.createCreditPaymentOfThird(creditPayment, bankAccountId);
    }
    @GetMapping(value = "get_last_ten_movements/{creditCardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<CreditPayment> getLastTenMovements(@PathVariable("creditCardId") String creditCardId){
        return creditPaymentService.getLastTen(creditCardId);
    }
}
