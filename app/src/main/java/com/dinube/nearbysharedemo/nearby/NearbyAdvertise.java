package com.dinube.nearbysharedemo.nearby;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.utils.UiUtils;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.Strategy;

public class NearbyAdvertise {

    public static void startAdvertising(Context context, String endpointName, RecyclerView recyclerView) {
        AdvertisingOptions advertisingOptions = new AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startAdvertising(endpointName, "com.dinube.nearbysharedemo",
                new NearbyConnectionLifeCycleCallback(context, recyclerView), advertisingOptions)
                .addOnSuccessListener((Void unused) -> {
                    UiUtils.showToast(context, "Advertising ", endpointName);
                })
                .addOnFailureListener((e) -> {
                    UiUtils.showToast(context, "Exception in start advertising ", e.getMessage());
                });
    }

    public static void stopAdvertising(Context context, String endpointName) {
        Nearby.getConnectionsClient(context).stopAdvertising();
        UiUtils.showToast(context, "Advertise Stopped ", endpointName);
    }

}
