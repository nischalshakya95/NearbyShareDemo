package com.dinube.nearbysharedemo.nearby;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.utils.UiUtils;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.Strategy;

public class NearbyDiscover {

    public static void startDiscovering(Context context, String endpointName, RecyclerView recyclerView) {
        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startDiscovery("com.dinube.nearbysharedemo",
                new NearbyEndPointDiscoveryCallback(context, endpointName, new NearbyConnectionLifeCycleCallback(context, recyclerView)), discoveryOptions)
                .addOnSuccessListener((Void unused) -> {
                    UiUtils.showToast(context, "Discovering from ", endpointName);
                })
                .addOnFailureListener((e) -> {
                    UiUtils.showToast(context, "Exception in discovering ", e.getMessage());
                });
    }

    public static void stopDiscovering(Context context, String endpointName) {
        Nearby.getConnectionsClient(context).stopDiscovery();
        UiUtils.showToast(context, "Discovering Stopped ", endpointName);
    }
}
