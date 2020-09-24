package com.dinube.supermarket.banks.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListData<T> {

    @SerializedName("data")
    private List<T> t;

    public List<T> getList() {
        return t;
    }

    public void setList(List<T> t) {
        this.t = t;
    }
}
