package com.dinube.supermarket.afterbank.response;

public class PaymentSuccessResponse {
    private String token;

    private String paymentId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
