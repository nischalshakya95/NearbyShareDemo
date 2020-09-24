package com.dinube.supermarket.afterbank.response;

public class PaymentInitiateResponse {

    private String follow;

    private String paymentId;

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
