package com.nttdata.bootcamp.assignement1.credits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit_card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Id
    Integer id;
    String holderName;
    String cardNumber;
    String expireMonth;
    String expireYear;
    String ccv;
}
