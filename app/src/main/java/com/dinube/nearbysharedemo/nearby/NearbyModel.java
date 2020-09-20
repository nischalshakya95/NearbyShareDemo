package com.dinube.nearbysharedemo.nearby;

public class NearbyModel {

    private String endpointName;

    private String endpointId;

    public NearbyModel(String endpointName, String endpointId) {
        this.endpointName = endpointName;
        this.endpointId = endpointId;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }
}
