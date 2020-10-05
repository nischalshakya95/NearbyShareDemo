package com.dinube.supermarket.dinube.model;


public class LoginResponse {
    private String authorizationToken;

    private String lastLogin;

    private String deviceType;

    private String phoneNumber;

    private String email;

    public LoginResponse(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "authorizationToken='" + authorizationToken + '\'' +
                '}';
    }
}
