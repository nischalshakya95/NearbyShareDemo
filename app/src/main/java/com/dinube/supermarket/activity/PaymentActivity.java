package com.dinube.supermarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.dinube.supermarket.R;

public class PaymentActivity extends AppCompatActivity {

    Button payWithNearbyShareButton;

    Button payWithQRScannerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payWithNearbyShareButton = findViewById(R.id.payUsingNearbyShareButton);
        payWithQRScannerButton = findViewById(R.id.payUsingQRScannerButton);

        payWithNearbyShareButton.setOnClickListener(l -> {
            initializePayWithNearby();
        });

        payWithQRScannerButton.setOnClickListener(click -> {
            initializePayWithNearby();
        });
    }

    private void initializePayWithNearby() {
        Intent intent = new Intent(getApplicationContext(), NearbyShareActivity.class);
        startActivity(intent);
    }
}