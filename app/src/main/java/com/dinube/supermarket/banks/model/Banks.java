package com.dinube.supermarket.banks.model;

import com.google.gson.annotations.SerializedName;

public class Banks {

    @SerializedName("countryCode")
    private String countryCode;

    @SerializedName("service")
    private String service;

    @SerializedName("swift")
    private String swift;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("image")
    private String image;

    @SerializedName("imageSVG")
    private String imageSVG;

    @SerializedName("paymentsSupported")
    private boolean paymentsSupported;

    public Banks(String countryCode, String service, String swift, String fullname, String image, String imageSVG, boolean paymentsSupported) {
        this.countryCode = countryCode;
        this.service = service;
        this.swift = swift;
        this.fullname = fullname;
        this.image = image;
        this.imageSVG = imageSVG;
        this.paymentsSupported = paymentsSupported;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageSVG() {
        return imageSVG;
    }

    public void setImageSVG(String imageSVG) {
        this.imageSVG = imageSVG;
    }

    public boolean isPaymentsSupported() {
        return paymentsSupported;
    }

    public void setPaymentsSupported(boolean paymentsSupported) {
        this.paymentsSupported = paymentsSupported;
    }
}
