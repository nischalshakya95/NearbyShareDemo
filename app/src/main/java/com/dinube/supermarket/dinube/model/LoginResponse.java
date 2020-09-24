package com.dinube.supermarket.dinube.model;


public class LoginResponse {
    public String authorizationToken;

    public LoginResponse(String authorizationToken) {
        this.authorizationToken = authorizationToken;
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
