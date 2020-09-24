package com.dinube.supermarket.nearbyshare;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.dinube.supermarket.utils.UiUtils;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.Strategy;

public class NearbyDiscover {

    public static void startDiscovering(Context context, String endpointName, RecyclerView recyclerView, PayloadCallback payloadCallback) {
        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startDiscovery("com.dinube.supermarket",
                new NearbyEndPointDiscoveryCallBack(context, endpointName, new NearbyConnectionLifeCycleCallBack(context, payloadCallback)), discoveryOptions)
                .addOnSuccessListener((Void unused) -> {
                    UiUtils.showToast(context, "Discovering from " + endpointName);
                })
                .addOnFailureListener((e) -> {
                    UiUtils.showToast(context, "Exception in discovering " + e.getMessage());
                });
    }

    public static void stopDiscovering(Context context, String endpointName) {
        Nearby.getConnectionsClient(context).stopDiscovery();
        UiUtils.showToast(context, "Discovering Stopped " + endpointName);
    }
}
