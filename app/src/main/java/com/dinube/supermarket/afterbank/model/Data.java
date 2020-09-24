package com.dinube.supermarket.afterbank.model;

import com.google.gson.annotations.SerializedName;

public class Data<T> {

    @SerializedName("data")
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
