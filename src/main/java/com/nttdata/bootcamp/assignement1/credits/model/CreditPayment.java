package com.nttdata.bootcamp.assignement1.credits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit_payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditPayment {
    @Id
    Integer id;
    String registerDate;
    double amount;
    Integer creditId;
}
