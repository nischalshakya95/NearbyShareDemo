package com.dinube.nearbysharedemo.nearby;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dinube.nearbysharedemo.utils.UiUtils;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;

public class NearbyEndPointDiscoveryCallback extends EndpointDiscoveryCallback {

    private Context context;

    private String endpointName;

    private ConnectionLifecycleCallback connectionLifecycleCallback;

    public NearbyEndPointDiscoveryCallback(Context context, String endpointName, ConnectionLifecycleCallback connectionLifecycleCallback) {
        this.context = context;
        this.endpointName = endpointName;
        this.connectionLifecycleCallback = connectionLifecycleCallback;
    }

    @Override
    public void onEndpointFound(@NonNull String s, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
        Nearby.getConnectionsClient(context).requestConnection(endpointName, s, connectionLifecycleCallback)
                .addOnSuccessListener((Void unused) -> {
                    UiUtils.showToast(context, "We successfully request the connection now both side must accept before the connection is established");
                })
                .addOnFailureListener((Exception e) -> {
                    UiUtils.showToast(context, "Exception in endpoint discovery callback ", e.getMessage());
                });
    }

    @Override
    public void onEndpointLost(@NonNull String s) {
        UiUtils.showToast(context, "On endpoint lost ", s);
    }

}
