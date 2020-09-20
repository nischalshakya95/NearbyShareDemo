package com.dinube.nearbysharedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.nearby.NearbyAdvertise;
import com.dinube.nearbysharedemo.nearby.NearbyDiscover;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private String endpointName;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        endpointName = Build.MODEL;
        recyclerView = findViewById(R.id.endpointsRecyclerView);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch advertiseSwitch = findViewById(R.id.advertiseSwitch);
        advertiseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                NearbyAdvertise.startAdvertising(context, endpointName, recyclerView);
            } else {
                NearbyAdvertise.stopAdvertising(context, endpointName);
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch discoverSwitch = findViewById(R.id.discoverSwitch);
        discoverSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                NearbyDiscover.startDiscovering(context, endpointName, recyclerView);
            } else {
                NearbyDiscover.stopDiscovering(context, endpointName);
            }
        });
    }
}