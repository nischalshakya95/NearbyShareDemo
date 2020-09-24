package com.dinube.supermarket.afterbank.exception;

import com.google.gson.annotations.SerializedName;

public class AfterBankException {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

