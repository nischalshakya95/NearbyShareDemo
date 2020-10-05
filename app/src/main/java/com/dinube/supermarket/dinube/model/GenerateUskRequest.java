package com.dinube.supermarket.dinube.model;

public class GenerateUskRequest {

    private String phoneNumber;

    private String authorizationToken;

    private Integer loyaltyCardId;

    private boolean autoSelectLoyalty;

    public GenerateUskRequest(){}

    public GenerateUskRequest(String phoneNumber, String authorizationToken, Integer loyaltyCardId, boolean autoSelectLoyalty) {
        this.phoneNumber = phoneNumber;
        this.authorizationToken = authorizationToken;
        this.loyaltyCardId = loyaltyCardId;
        this.autoSelectLoyalty = autoSelectLoyalty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public Integer getLoyaltyCardId() {
        return loyaltyCardId;
    }

    public void setLoyaltyCardId(Integer loyaltyCardId) {
        this.loyaltyCardId = loyaltyCardId;
    }

    public boolean isAutoSelectLoyalty() {
        return autoSelectLoyalty;
    }

    public void setAutoSelectLoyalty(boolean autoSelectLoyalty) {
        this.autoSelectLoyalty = autoSelectLoyalty;
    }
}
