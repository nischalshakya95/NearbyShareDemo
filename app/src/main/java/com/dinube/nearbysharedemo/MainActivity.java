package com.dinube.nearbysharedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.fragment.EndpointsListAdapter;
import com.dinube.nearbysharedemo.nearby.NearbyConnectionLifeCycleCallback;
import com.dinube.nearbysharedemo.nearby.NearbyEndPointDiscoveryCallback;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.Strategy;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private String endpointName;

    private RecyclerView recyclerView;

    private final List<String> endpoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        endpointName = Build.MODEL;
        recyclerView = findViewById(R.id.endpointsRecyclerView);

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
        recyclerView = findViewById(R.id.endpointsRecyclerView);
        EndpointsListAdapter adapter = new EndpointsListAdapter(context, endpoints);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void startAdvertising() {
        AdvertisingOptions advertisingOptions = new AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startAdvertising(endpointName, "com.dinube.nearbysharedemo", new NearbyConnectionLifeCycleCallback(context, recyclerView), advertisingOptions)
                .addOnSuccessListener((Void unused) -> {
                    showToast("Advertising ", endpointName);
                })
                .addOnFailureListener((e) -> {
                    showToast("Exception in start advertising ", e.getMessage());
                });
    }

    private void startDiscovering() {
        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build();
        Nearby.getConnectionsClient(context).startDiscovery("com.dinube.nearbysharedemo",
                new NearbyEndPointDiscoveryCallback(context, endpointName, new NearbyConnectionLifeCycleCallback(context, recyclerView)), discoveryOptions)
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