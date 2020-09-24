package com.dinube.supermarket.afterbank.request;

import com.dinube.supermarket.utils.TempVariables;

public class PaymentInitiateRequest {

    private String token;

    private String paymentType;

    private String currency;

    private Integer amount;

    private String sourceIBAN;

    private String destinationIBAN;

    private String destinationCreditorName;

    private String paymentDescription;

    private String yourPaymentCallback;

    private String urlRedirect;

    public PaymentInitiateRequest() {
    }

    public PaymentInitiateRequest(Integer amount) {
        this.amount = amount;
        this.token = "sandbox.r1v4qkan";
        this.paymentType = "normal";
        this.currency = "EUR";
        this.sourceIBAN = TempVariables.SOURCE_IBAN_NUMBER;
        this.destinationIBAN = "ES1801822200120201933578";
        this.destinationCreditorName = "Nischal Shakya";
        this.paymentDescription = "Utility Purpose";
        this.yourPaymentCallback = TempVariables.AFTER_BANK_BASE_URL + "/payment/initiate/callback";
        this.urlRedirect = TempVariables.AFTER_BANK_BASE_URL + "/payment/initiate/response";
    }

    public PaymentInitiateRequest(String token, String paymentType, String currency, Integer amount, String sourceIBAN, String destinationIBAN, String destinationCreditorName, String paymentDescription, String yourPaymentCallback, String urlRedirect) {
        this.token = token;
        this.paymentType = paymentType;
        this.currency = currency;
        this.amount = amount;
        this.sourceIBAN = sourceIBAN;
        this.destinationIBAN = destinationIBAN;
        this.destinationCreditorName = destinationCreditorName;
        this.paymentDescription = paymentDescription;
        this.yourPaymentCallback = yourPaymentCallback;
        this.urlRedirect = urlRedirect;
    }
}
