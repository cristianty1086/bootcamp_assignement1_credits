package com.nttdata.bootcamp.assignement1.credits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
    @Id
    Integer id;
    double limitAmount;
    String nextDatePayment;
    String nextAmountPayment;
    double currentBalance;
    CreditCard creditCard;
    Integer costumer_id;
    String costumerType;
    CreditType creditType;
    Integer maxCredit;
}
