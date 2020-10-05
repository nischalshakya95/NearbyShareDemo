package com.dinube.supermarket.nearbyshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dinube.supermarket.utils.UiUtils;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;

public class NearbyConnectionLifeCycleCallBack extends ConnectionLifecycleCallback {

    private Context context;

    private PayloadCallback payloadCallback;

    private String nfcUsk;

    public NearbyConnectionLifeCycleCallBack(Context context, PayloadCallback payloadCallback, String nfcUsk) {
        this.context = context;
        this.payloadCallback = payloadCallback;
        this.nfcUsk = nfcUsk;
    }

    @Override
    public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
        UiUtils.showToast(context, "Endpoints " + connectionInfo.getEndpointName());
        new AlertDialog.Builder(context)
                .setTitle("Accept connection to " + connectionInfo.getEndpointName())
                .setMessage("Confirm the code matches on both devices: " + connectionInfo.getAuthenticationToken())
                .setPositiveButton(
                        "Accept",
                        (DialogInterface dialog, int which) ->
                                // The user confirmed, so we can accept the connection.
                                Nearby.getConnectionsClient(context)
                                        .acceptConnection(endpointId, payloadCallback))
                .setNegativeButton(
                        android.R.string.cancel,
                        (DialogInterface dialog, int which) ->
                                // The user canceled, so we should reject the connection.
                                Nearby.getConnectionsClient(context).rejectConnection(endpointId))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onConnectionResult(@NonNull String endpointId, @NonNull ConnectionResolution connectionResolution) {
        switch (connectionResolution.getStatus().getStatusCode()) {
            case ConnectionsStatusCodes.STATUS_OK:
                Payload bytesPayload = Payload.fromBytes(nfcUsk.getBytes());
                Nearby.getConnectionsClient(context).sendPayload(endpointId, bytesPayload);
                break;
            case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                Toast.makeText(context, "Connection rejected", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionsStatusCodes.STATUS_ERROR:
                Toast.makeText(context, "Connection status error", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "Unknown status code", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDisconnected(@NonNull String s) {
        UiUtils.showToast(context, "We've been disconnected from the endpoint. No more data can be send or received.");
    }
}
