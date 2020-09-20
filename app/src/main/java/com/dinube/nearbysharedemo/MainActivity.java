package com.dinube.nearbysharedemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.fragment.EndpointsListAdapter;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private String endpointName;

    private final List<String> endpoints = new ArrayList<>();

    private ConnectionInfo connection;

    private String endId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        endpointName = Build.MODEL;

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch advertiseSwitch = findViewById(R.id.advertiseSwitch);
        advertiseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startAdvertising();
            } else {
                stopAdvertising();
                endpoints.clear();
                generateDataList(endpoints);
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch discoverSwitch = findViewById(R.id.discoverSwitch);
        discoverSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startDiscovering();
            } else {
                stopDiscovering();
                endpoints.clear();
                generateDataList(endpoints);
            }
        });
    }

    private void generateDataList(List<String> endpoints) {
        RecyclerView recyclerView = findViewById(R.id.endpointsRecyclerView);
        recyclerView.removeAllViews();
    }

    private final EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {
        @Override
        public void onEndpointFound(@NonNull String s, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
            Nearby.getConnectionsClient(context).requestConnection(endpointName, s, connectionLifecycleCallback)
                    .addOnSuccessListener((Void unused) -> {
                        Toast.makeText(context,
                                "We successfully request the connection now both side must accept before the connection is established",
                                Toast.LENGTH_SHORT)
                                .show();
                    })
                    .addOnFailureListener((Exception e) -> {
                        showToast("Exception in endpoint discovery callback ", e.getMessage());
                    });
        }

        @Override
        public void onEndpointLost(@NonNull String s) {
            showToast("On endpoint lost ", s);
        }
    };

    private final ConnectionLifecycleCallback connectionLifecycleCallback = new ConnectionLifecycleCallback() {

        private ConnectionInfo connectionInfo;

        private String endpointId;

        @Override
        public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
            showToast("Endpoints ", connectionInfo.getEndpointName());
            endpoints.add(connectionInfo.getEndpointName());
            generateDataList(endpoints);
            this.connectionInfo = connectionInfo;
            this.endpointId = endpointId;
        }

        private void generateDataList(List<String> endpoints) {
            RecyclerView recyclerView = findViewById(R.id.endpointsRecyclerView);
            EndpointsListAdapter adapter = new EndpointsListAdapter(context, endpoints);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.onSetClickListener(onClickListener);
        }


        private View.OnClickListener onClickListener = view -> {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            String endpoint = endpoints.get(viewHolder.getAdapterPosition());
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Accept connection to " + connectionInfo.getEndpointName())
                    .setMessage("Confirm the code matches on both devices: " + connectionInfo.getAuthenticationToken())
                    .setPositiveButton(
                            "Accept",
                            (DialogInterface dialog, int which) ->
                                    // The user confirmed, so we can accept the connection.
                                    Nearby.getConnectionsClient(context)
                                            .acceptConnection(endId, payloadCallback))
                    .setNegativeButton(
                            android.R.string.cancel,
                            (DialogInterface dialog, int which) ->
                                    // The user canceled, so we should reject the connection.
                                    Nearby.getConnectionsClient(context).rejectConnection(endpointId))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        };

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

        @Override
        public void onDisconnected(@NonNull String s) {
            Toast.makeText(context, "We've been disconnected from the endpoint. No more data can be send or received.", Toast.LENGTH_SHORT).show();
        }
    };

    public PayloadCallback payloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(@NonNull String endpointId, Payload payload) {
                    // This always gets the full data of the payload. Will be null if it's not a BYTES
                    // payload. You can check the payload type with payload.getType().
                    byte[] receivedBytes = payload.asBytes();
                }

                @Override
                public void onPayloadTransferUpdate(@NonNull String endpointId, @NonNull PayloadTransferUpdate update) {
                    // Bytes payloads are sent as a single chunk, so you'll receive a SUCCESS update immediately
                    // after the call to onPayloadReceived().
                }
            };

    private void startAdvertising() {
        AdvertisingOptions advertisingOptions = new AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startAdvertising(endpointName, "com.dinube.nearbysharedemo", connectionLifecycleCallback, advertisingOptions)
                .addOnSuccessListener((Void unused) -> {
                    showToast("Advertising ", endpointName);
                })
                .addOnFailureListener((e) -> {
                    showToast("Exception in start advertising ", e.getMessage());
                });
    }

    private void startDiscovering() {
        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startDiscovery("com.dinube.nearbysharedemo", endpointDiscoveryCallback, discoveryOptions)
                .addOnSuccessListener((Void unused) -> {
                    showToast("Discovering from ", endpointName);
                })
                .addOnFailureListener((e) -> {
                    showToast("Exception in discovering ", e.getMessage());
                });
    }

    private void stopAdvertising() {
        Nearby.getConnectionsClient(context).stopAdvertising();
        showToast("Advertise Stopped ", endpointName);
    }

    private void stopDiscovering() {
        Nearby.getConnectionsClient(context).stopDiscovery();
        showToast("Discovering Stopped ", endpointName);
    }

    private void showToast(String s, String endpointName) {
        Toast.makeText(context, s + endpointName, Toast.LENGTH_SHORT).show();
    }
}