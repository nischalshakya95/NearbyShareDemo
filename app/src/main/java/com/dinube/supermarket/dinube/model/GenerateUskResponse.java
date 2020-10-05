package com.dinube.supermarket.dinube.model;

public class GenerateUskResponse {

    private String shortUsk;

    private String qrUsk;

    private String nfcUsk;

    private Integer validTo;

    public GenerateUskResponse() {
    }

    public GenerateUskResponse(String shortUsk, String qrUsk, String nfcUsk, Integer validTo) {
        this.shortUsk = shortUsk;
        this.qrUsk = qrUsk;
        this.nfcUsk = nfcUsk;
        this.validTo = validTo;
    }

    public String getShortUsk() {
        return shortUsk;
    }

    public void setShortUsk(String shortUsk) {
        this.shortUsk = shortUsk;
    }

    public String getQrUsk() {
        return qrUsk;
    }

    public void setQrUsk(String qrUsk) {
        this.qrUsk = qrUsk;
    }

    public String getNfcUsk() {
        return nfcUsk;
    }

    public void setNfcUsk(String nfcUsk) {
        this.nfcUsk = nfcUsk;
    }

    public Integer getValidTo() {
        return validTo;
    }

    public void setValidTo(Integer validTo) {
        this.validTo = validTo;
    }
}
