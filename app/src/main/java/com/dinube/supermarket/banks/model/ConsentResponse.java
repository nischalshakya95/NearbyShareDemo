package com.dinube.supermarket.banks.model;

import com.google.gson.annotations.SerializedName;

public class ConsentResponse {

    @SerializedName("follow")
    private String follow;

    @SerializedName("consentId")
    private String consentId;

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    @Override
    public String toString() {
        return "ConsentResponse{" +
                "follow='" + follow + '\'' +
                ", consentId='" + consentId + '\'' +
                '}';
    }
}
