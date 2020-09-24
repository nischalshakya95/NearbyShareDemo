package com.dinube.supermarket.dinube.model;

public class LoginRequest {
    private String  phoneNumber;
    private String password;
    private String deviceId;
    private String deviceOS;
    private String deviceType;
    private String appVersion;

    public LoginRequest(String phoneNumber, String password, String deviceId, String deviceOS, String deviceType, String appVersion) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.deviceId = deviceId;
        this.deviceOS = deviceOS;
        this.deviceType = deviceType;
        this.appVersion = appVersion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}