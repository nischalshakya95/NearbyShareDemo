package com.dinube.supermarket.nearbyshare;

import android.content.Context;

import com.dinube.supermarket.utils.UiUtils;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.Strategy;

public class NearbyAdvertise {

    // Note: SERVICE_ID MUST BE SAME OF START DISCOVERY SECTION OF NEARBY SHARE ELSE THE DEVICE WONT BE DISCOVERABLE
    private static final String SERVICE_ID = "com.dinube.nearbysharedemo";

    public static void startAdvertising(Context context, String endpointName, PayloadCallback payloadCallback) {
        AdvertisingOptions advertisingOptions = new AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startAdvertising(endpointName, SERVICE_ID,
                new NearbyConnectionLifeCycleCallBack(context, payloadCallback), advertisingOptions)
                .addOnSuccessListener((Void unused) -> {
                    UiUtils.showToast(context, "Advertising " + endpointName);
                })
                .addOnFailureListener((e) -> {
                    UiUtils.showToast(context, "Exception in start advertising " + e.getMessage());
                });
    }

    public static void stopAdvertising(Context context, String endpointName) {
        Nearby.getConnectionsClient(context).stopAdvertising();
        Nearby.getConnectionsClient(context).stopAllEndpoints();
        UiUtils.showToast(context, "Advertise Stopped " + endpointName);
    }
}
