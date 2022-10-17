package com.nttdata.bootcamp.assignement1.credits.aplication;

import com.nttdata.bootcamp.assignement1.credits.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("credit")
public class CreditController {

    @Autowired
    CreditService creditService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Credit> createCredit(@RequestBody Credit credit){
        System.out.println(credit);
        return creditService.createCredit(Mono.just(credit));
    }

    @GetMapping(value = "get/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Credit> getCreditById(@PathVariable("id") Integer id){
        return creditService.readCredit(id);
    }

    @PutMapping(value = "update/{id}")
    @ResponseBody
    public Mono<Credit> updateCredit(@RequestBody Credit credit){
        return creditService.updateCredit(credit);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteCreditById(@PathVariable("id") Integer id){
        return creditService.deleteCredit(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Credit> listarTodos(){
        return creditService.listarTodos();
    }

}
