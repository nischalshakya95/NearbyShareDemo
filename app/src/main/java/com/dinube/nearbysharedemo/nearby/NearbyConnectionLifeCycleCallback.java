package com.dinube.nearbysharedemo.nearby;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.fragment.EndpointsListAdapter;
import com.dinube.nearbysharedemo.utils.UiUtils;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;

import java.util.ArrayList;
import java.util.List;

public class NearbyConnectionLifeCycleCallback extends ConnectionLifecycleCallback {

    private Context context;

    private RecyclerView recyclerView;

    private ConnectionInfo connectionInfo;

    private String endpointId;

    private final List<String> endpoints = new ArrayList<>();

    public NearbyConnectionLifeCycleCallback(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
        UiUtils.showToast(context, "Endpoints ", connectionInfo.getEndpointName());
        endpoints.add(endpointId);
        generateDataList(endpoints);
        this.connectionInfo = connectionInfo;
    }

    @Override
    public void onConnectionResult(@NonNull String s, @NonNull ConnectionResolution connectionResolution) {
        switch (connectionResolution.getStatus().getStatusCode()) {
            case ConnectionsStatusCodes.STATUS_OK:
                Toast.makeText(context, "We're connected! Can now start sending and receiving data", Toast.LENGTH_SHORT).show();
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

    private void generateDataList(List<String> endpoints) {
        EndpointsListAdapter adapter = new EndpointsListAdapter(context, endpoints);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.onSetClickListener(onClickListener);
    }


    private View.OnClickListener onClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        this.endpointId = endpoints.get(viewHolder.getAdapterPosition());
        new AlertDialog.Builder(context)
                .setTitle("Accept connection to " + connectionInfo.getEndpointName())
                .setMessage("Confirm the code matches on both devices: " + connectionInfo.getAuthenticationToken())
                .setPositiveButton(
                        "Accept",
                        (DialogInterface dialog, int which) ->
                                // The user confirmed, so we can accept the connection.
                                Nearby.getConnectionsClient(context)
                                        .acceptConnection(endpointId, new NearbyPayloadCallback()))
                .setNegativeButton(
                        android.R.string.cancel,
                        (DialogInterface dialog, int which) ->
                                // The user canceled, so we should reject the connection.
                                Nearby.getConnectionsClient(context).rejectConnection(this.endpointId))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    };

    @Override
    public void onDisconnected(@NonNull String s) {
        UiUtils.showToast(context, "We've been disconnected from the endpoint. No more data can be send or received.");
    }

    static class NearbyPayloadCallback extends PayloadCallback {

        @Override
        public void onPayloadReceived(@NonNull String s, @NonNull Payload payload) {
            byte[] receivedBytes = payload.asBytes();
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {

        }
    }
}