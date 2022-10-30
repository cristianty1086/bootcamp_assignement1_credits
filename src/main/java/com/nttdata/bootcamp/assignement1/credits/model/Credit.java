package com.nttdata.bootcamp.assignement1.credits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
    @Id
    String id;
    double limitAmount;
    String nextDatePayment;
    String nextAmountPayment;
    double currentBalance;
    List<CreditCard> creditCard;
    BigInteger costumerId;
    String costumerType;
    CreditType creditType;
}
