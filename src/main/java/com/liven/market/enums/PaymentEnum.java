package com.liven.market.enums;

public enum PaymentEnum {
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    PAYPAL("PAYPAL"),
    CASH("CASH"),
    BILLET("BILLET");

    private String description;

    PaymentEnum(String description) {
        this.description = description;
    }
}
