package com.nttdata.bootcamp.assignement1.credits.utilities;


import com.nttdata.bootcamp.assignement1.credits.model.CreditCard;

import java.math.BigInteger;

public class BuilderUrl {
    public static String buildCountCreditsByCostumerId(BigInteger costumerId) {
        if(costumerId == null) {
            return  null;
        }

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/credit/count_credits/");
        ss.append(costumerId);

        return ss.toString();
    }
    public static String buildCreateCreditCard() {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/credit_card/create");

        return ss.toString();
    }
    public static String buildGetCredit(String creditId) {
        if(creditId == null) {
            return  null;
        }
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/credit/get/");
        ss.append(creditId);

        return ss.toString();
    }
    public static String buildUpdateCredit() {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/credit/update");

        return ss.toString();
    }
    public static String buildGetCreditsByCostumerId(BigInteger costumerId) {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/credit/get_by_costumer");
        ss.append(costumerId);

        return ss.toString();
    }
    public static String buildCreateMovementInCostumerId() {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlCredits);
        ss.append("/movement/create");

        return ss.toString();
    }
}
